package kg.rysbaevich.aist.model.dto.billing;

import kg.rysbaevich.aist.enums.Currency;
import kg.rysbaevich.aist.enums.SubscriptionName;
import kg.rysbaevich.aist.model.entity.billing.SubscriptionType;

import java.math.BigDecimal;

public record SubscriptionTypeDto(

        long id,

        SubscriptionName name,

        int periodInMonth,

        BigDecimal price,

        Currency currency
) {

    public SubscriptionTypeDto(SubscriptionType subscriptionType) {
        this(subscriptionType.getId(), subscriptionType.getName(), subscriptionType.getPeriodInMonth(), subscriptionType.getPrice(), subscriptionType.getCurrency());
    }

}
