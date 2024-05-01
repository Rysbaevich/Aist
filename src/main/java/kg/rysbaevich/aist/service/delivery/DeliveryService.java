package kg.rysbaevich.aist.service.delivery;

import kg.rysbaevich.aist.model.request.delivery.DeliveryRequest;
import kg.rysbaevich.aist.model.response.delivery.DeliveryResponse;
import kg.rysbaevich.aist.enums.Transport;

import java.time.LocalDate;

public interface DeliveryService {
    Long save(Transport transport, DeliveryRequest deliveryRequest, String customerId);

    DeliveryResponse findAllByFromToAndWhen(String fromLocation, String toLocation, LocalDate departureDate, LocalDate arrivalDate, int pageNumber, int pageSize);
    DeliveryResponse findByCustomerId(String customerId, int pageNumber, int pageSize);
}
