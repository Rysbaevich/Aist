package kg.rysbaevich.aist.model.response.billing;

public record PaymentResponse(
        String paymentId,
        String confirmationUrl
) {
}
