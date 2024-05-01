package kg.rysbaevich.aist.model.dto.delivery;

import kg.rysbaevich.aist.model.entity.delivery.Delivery;

import java.time.LocalDate;

public record DeliveryDto(
        Long id,
        Long packageId,
        String customerId,
        String fromLocation,
        String toLocation,
        LocalDate departureDate,
        LocalDate arrivalDate,
        String description
) {
    public DeliveryDto(Delivery delivery) {
        this(delivery.getId(), delivery.getPackageId(), delivery.getCustomerId(), delivery.getFromLocation(),
                delivery.getToLocation(), delivery.getArrivalDate(), delivery.getArrivalDate(), delivery.getDescription());
    }
}
