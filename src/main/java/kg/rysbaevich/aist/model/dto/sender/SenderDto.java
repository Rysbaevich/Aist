package kg.rysbaevich.aist.model.dto.sender;

import kg.rysbaevich.aist.model.entity.sender.Sender;

import java.time.LocalDate;

public record SenderDto(
        Long id,
        Long packageId,
        String customerId,
        String fromLocation,
        String toLocation,
        LocalDate departureDate,
        LocalDate arrivalDate,
        String description
) {
    public SenderDto(Sender sender) {
        this(sender.getId(), sender.getPackageId(), sender.getCustomerId(), sender.getFromLocation(),
                sender.getToLocation(), sender.getArrivalDate(), sender.getArrivalDate(), sender.getDescription());
    }
}
