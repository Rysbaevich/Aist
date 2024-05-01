package kg.rysbaevich.aist.service.billing.impl;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import kg.rysbaevich.aist.exceptions.PaymentException;
import kg.rysbaevich.aist.feign.Yookassa;
import kg.rysbaevich.aist.model.entity.billing.Transaction;
import kg.rysbaevich.aist.model.request.billing.NotificationRequest;
import kg.rysbaevich.aist.model.request.billing.PaymentRequest;
import kg.rysbaevich.aist.model.request.billing.PaymentRequestByCard;
import kg.rysbaevich.aist.model.response.billing.Payment;
import kg.rysbaevich.aist.model.response.billing.PaymentResponse;
import kg.rysbaevich.aist.repository.billing.TransactionRepository;
import kg.rysbaevich.aist.service.billing.SubscriptionService;
import kg.rysbaevich.aist.service.billing.SubscriptionTypeService;
import kg.rysbaevich.aist.service.billing.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final Yookassa yookassa;
    private final SubscriptionTypeService subscriptionTypeService;
    private final TransactionRepository transactionRepository;
    private final SubscriptionService subscriptionService;

    @Override
    public PaymentResponse pay(String customerId, String paymentToken, Long subscriptionTypeId) {

        log.info("customerId: {}, paymentToken: {}, subscriptionTypeId: {}", customerId, paymentToken, subscriptionTypeId);

        var subsType = subscriptionTypeService.getById(subscriptionTypeId);

        String description = "Подключение тарифа «" + subsType.name().name() + "» за " + (subsType.periodInMonth() * 30) + " дней";

        Payment payment;

        if (paymentToken == null) {
            PaymentRequestByCard.Amount amount = new PaymentRequestByCard.Amount(subsType.price(), subsType.currency().name());
            PaymentRequestByCard.PaymentMethodData pmd = new PaymentRequestByCard.PaymentMethodData("bank_card");
            PaymentRequestByCard.Confirmation confirmation = new PaymentRequestByCard.Confirmation("redirect", "https://spring.io");
            PaymentRequestByCard paymentRequestByCard = new PaymentRequestByCard(
                    amount, pmd, confirmation,
                    description, true, new PaymentRequestByCard.Metadata(customerId, subscriptionTypeId));

            payment = yookassa.paymentsByCard(UUID.randomUUID().toString(), paymentRequestByCard);
            if (payment.status() != Payment.Status.pending) {
                throw new PaymentException("Payment failed, status is not pending");
            }
        } else {
            PaymentRequest.Amount amount = new PaymentRequest.Amount(subsType.price(), subsType.currency().name());
            PaymentRequest paymentRequest = new PaymentRequest(paymentToken,
                    amount,
                    true,
                    description,
                    new PaymentRequest.Metadata(customerId, subscriptionTypeId));

            payment = yookassa.payments(UUID.randomUUID().toString(), paymentRequest);
        }

        log.info("payment: {}", payment);

        Transaction transaction = new Transaction();
        transaction.setPaymentId(UUID.fromString(payment.id()));
        transaction.setCustomerId(customerId);
        transaction.setStatus(payment.status());
        transaction.setPayment(payment);
        transactionRepository.save(transaction);

        return new PaymentResponse(payment.id(),
                (payment.confirmation() != null && payment.confirmation().confirmation_url() != null)
                        ? payment.confirmation().confirmation_url()
                        : null
        );
    }

    @Override
    public Payment.Status checkStatus(String paymentId) {
        Payment payment = yookassa.getPaymentStatus(paymentId);
        return payment.status();
    }

    @Override
    @Transactional
    public void notificationAboutStatus(NotificationRequest request) {

        Gson gson = new Gson();
        log.info("#notificationAboutStatus() request: {}", gson.toJson(request));

        if (!request.event().equals("payment.succeeded")) {
            log.error("#notificationAboutStatus() not succeeded status on notification request: {}", request);
            throw new PaymentException("Non valid status");
        }

        Transaction transaction = new Transaction();
        transaction.setCustomerId(request.object().merchant_customer_id());
        transaction.setPaymentId(UUID.fromString(request.object().id()));
        transaction.setPayment(request.object());
        transaction.setStatus(Payment.Status.succeeded);
        transaction = transactionRepository.save(transaction);

        String customerId = request.object().metadata().customerId();
        Long transactionId = transaction.getId();
        Long subscriptionTypeId = request.object().metadata().subscriptionTypeId();

        subscriptionService.saveFromTransactionNotification(customerId, transactionId, subscriptionTypeId);
    }
}
