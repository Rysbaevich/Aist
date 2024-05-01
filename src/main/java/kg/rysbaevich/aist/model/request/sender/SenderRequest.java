package kg.rysbaevich.aist.model.request.sender;

import java.time.LocalDate;

public record SenderRequest(
        Long packageId,
        String fromLocation,
        String toLocation,
        LocalDate departureDate,
        LocalDate arrivalDate,
        String description
){
}
