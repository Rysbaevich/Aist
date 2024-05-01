package kg.rysbaevich.aist.model.request.billing;

import kg.rysbaevich.aist.model.response.billing.Payment;

public record NotificationRequest(
        String type,
        String event,
        Payment object
) {}
