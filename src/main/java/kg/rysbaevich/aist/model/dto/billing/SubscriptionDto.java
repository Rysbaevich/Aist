package kg.rysbaevich.aist.model.dto.billing;


import kg.rysbaevich.aist.enums.SubscriptionName;

import java.time.LocalDateTime;

public record SubscriptionDto(
    SubscriptionName subscriptionName,
    LocalDateTime startDate,
    LocalDateTime endDate,
    boolean isActive) {
}
