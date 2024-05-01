package kg.rysbaevich.aist.model.request.billing;

import java.math.BigDecimal;

public record PaymentRequest(
    String payment_token,
    Amount amount,
    boolean capture,
    String description,
    Metadata metadata
) {
    public record Amount(
            BigDecimal value,
            String currency
    ) {}

    public record Metadata(
            String customerId,
            Long subscriptionTypeId
    ) {}
}
