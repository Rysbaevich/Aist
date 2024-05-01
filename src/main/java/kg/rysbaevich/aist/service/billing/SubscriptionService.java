package kg.rysbaevich.aist.service.billing;


import kg.rysbaevich.aist.model.dto.billing.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionDto> getActiveSubscription(String customerId);
    void saveFromTransactionNotification(String customerId, Long transactionId, Long subscriptionTypeId);
    void refreshSubscriptions();
}
