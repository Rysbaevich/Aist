package kg.rysbaevich.aist.model.response.billing;

import kg.rysbaevich.aist.model.request.billing.PaymentRequestByCard;

import java.util.Date;

public record Payment(

        String id,
        Status status,
        Amount amount,
        IncomeAmount income_amount,
        String description,
        Recipient recipient,
        PaymentMethod payment_method,
        Date captured_at,
        Date created_at,
        Date expires_at,
        Confirmation confirmation,
        boolean test,
        Amount refunded_amount,
        boolean paid,
        boolean refundable,
        ReceiptRegistration receipt_registration,
        PaymentRequestByCard.Metadata metadata,
        CancellationDetails cancellation_details,
        AuthorizationDetails authorization_details,
        String merchant_customer_id

) {
    public enum ReceiptRegistration {pending, succeeded, canceled}

    public enum Status {pending, waiting_for_capture, succeeded, canceled}

    public record CancellationDetails(String party, String reason) {
    }

    public record Amount(String value, String currency) {
    }

    public record AuthorizationDetails(String rrn, String auth_code, ThreeDSecure three_d_secure) {
    }

    public record Confirmation(String type, String return_url, String confirmation_url) {
    }

    public record Card(String first6, String last4, String expiry_month, String expiry_year, String card_type,
                       String issuer_country, String issuer_name) {
    }

    public record IncomeAmount(String value, String currency) {
    }

    public record PaymentMethod(String type, String id, boolean saved, Card card, String title) {
    }

    public record Recipient(String account_id, String gateway_id) {
    }

    public record ThreeDSecure(boolean applied) {
    }

}
