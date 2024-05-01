package kg.rysbaevich.aist.model.request.delivery;

import java.time.LocalDate;

public record DeliveryRequest(
        Long packageId,
        String fromLocation,
        String toLocation,
        LocalDate departureDate,
        LocalDate arrivalDate,
        String description
) {
}
