package kg.rysbaevich.aist.service.billing.impl;

import com.google.firebase.auth.FirebaseAuthException;
import kg.rysbaevich.aist.enums.SubscriptionName;
import kg.rysbaevich.aist.model.dto.billing.SubscriptionDto;
import kg.rysbaevich.aist.model.entity.billing.Subscription;
import kg.rysbaevich.aist.model.entity.billing.SubscriptionType;
import kg.rysbaevich.aist.model.entity.billing.Transaction;
import kg.rysbaevich.aist.repository.billing.SubscriptionRepository;
import kg.rysbaevich.aist.repository.customer.CustomerIdProjection;
import kg.rysbaevich.aist.service.billing.SubscriptionService;
import kg.rysbaevich.aist.service.billing.SubscriptionTypeService;
import kg.rysbaevich.aist.service.customer.impl.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionTypeService subscriptionTypeService;
    private final UserManagementService userManagementService;

    @Override
    public List<SubscriptionDto> getActiveSubscription(String customerId) {
        List<Subscription> list = subscriptionRepository.findAllActiveByCustomerIdNow(customerId);
        List<SubscriptionDto> subscriptionDtoList = new ArrayList<>();

        Set<SubscriptionName> subscriptionNameSet = new HashSet<>();

        for (Subscription subscription : list) {
            subscriptionNameSet.add(subscription.getType().getName());
            subscriptionDtoList.add(new SubscriptionDto(subscription.getType().getName(), subscription.getStartDate(), subscription.getEndDate(), true));
        }

        var typeList = subscriptionTypeService.getAllExcept(subscriptionNameSet);

        for (SubscriptionName name : typeList) {
            subscriptionDtoList.add(new SubscriptionDto(name, null, null, false));
        }
        return subscriptionDtoList;
    }

    @Transactional
    public void refreshSubscriptions() {
        Set<CustomerIdProjection> customers = subscriptionRepository.findAllActiveAndEndDateIsBeforeNow();
        log.info("#refreshSubscriptions() @ start");
        for (CustomerIdProjection customerIdProjection : customers) {
            log.info("#refreshSubscriptions() @ {}", customerIdProjection.getCustomerId());
            refreshSubscriptionForUser(customerIdProjection.getCustomerId());
        }
        log.info("#refreshSubscriptions() @ end");
    }

    @Override
    @Transactional
    public void saveFromTransactionNotification(String customerId, Long transactionId, Long subscriptionTypeId) {

        log.info("#saveFromTransactionNotification() customerId: {}, transactionId: {}, subscriptionTypeId: {}", customerId, transactionId, subscriptionTypeId);

        var subscriptionTypeDto = subscriptionTypeService.getById(subscriptionTypeId);
        var lastSubscriptionByType = subscriptionRepository.findLastSubscriptionByCustomerIdAndTypeId(customerId, subscriptionTypeId);

        LocalDateTime startDate = LocalDateTime.now();

        if (lastSubscriptionByType.isPresent()) {
            startDate = lastSubscriptionByType.get().getEndDate();
        }

        LocalDateTime endDate = startDate.plusMonths(subscriptionTypeDto.periodInMonth());

        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setCustomerId(customerId);

        Subscription subscription = new Subscription();
        subscription.setCustomerId(customerId);
        subscription.setTransaction(transaction);
        subscription.setType(new SubscriptionType(subscriptionTypeDto));
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        subscription.setActive(true);
        subscriptionRepository.save(subscription);

        refreshSubscriptionForUser(customerId);
    }

    @Transactional
    protected void refreshSubscriptionForUser(String customerId) {
        var expiredSubscriptions = subscriptionRepository.findAllByCustomerIdActiveAndEndDateIsBeforeNow(customerId);
        if (!expiredSubscriptions.isEmpty()) {
            for (Subscription s : expiredSubscriptions) {
                s.setActive(false);
                subscriptionRepository.save(s);
            }
        }

        List<SubscriptionDto> list = getActiveSubscription(customerId);
        Set<SubscriptionName> permissionList = new HashSet<>();

        for (SubscriptionDto s : list) {
            permissionList.add(s.subscriptionName());
        }

        try {
            userManagementService.setUserClaims(customerId, permissionList);
            log.info("#refreshSubscriptionForUser() @ {} --> {}", customerId, permissionList);
        } catch (FirebaseAuthException e) {
            log.error("#refreshSubscriptionForUser() {} --> {}", customerId, e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
