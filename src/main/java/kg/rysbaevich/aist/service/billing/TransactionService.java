package kg.rysbaevich.aist.service.billing;

import kg.rysbaevich.aist.model.request.billing.NotificationRequest;
import kg.rysbaevich.aist.model.response.billing.Payment;
import kg.rysbaevich.aist.model.response.billing.PaymentResponse;

public interface TransactionService {
    PaymentResponse pay(String customerId, String paymentToken, Long subscriptionTypeId);
    Payment.Status checkStatus(String paymentId);
    void notificationAboutStatus(NotificationRequest request);
}
