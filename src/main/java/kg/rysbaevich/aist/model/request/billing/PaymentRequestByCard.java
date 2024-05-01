package kg.rysbaevich.aist.model.request.billing;

import java.math.BigDecimal;

public record PaymentRequestByCard(
        Amount amount,
        PaymentMethodData payment_method_data,
        Confirmation confirmation,
        String description,
        boolean capture,
        Metadata metadata
        ) {

    public record Amount(
            BigDecimal value,
            String currency
    ) {}

    public record PaymentMethodData(
            String type
    ) {}

    public record Confirmation(
            String type,
            String return_url
    ) {}

    public record Metadata(
            String customerId,
            Long subscriptionTypeId
    ) {}
}
