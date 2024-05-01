package kg.rysbaevich.aist.model.response.delivery;

import kg.rysbaevich.aist.model.dto.delivery.DeliveryDto;

import java.util.List;

public record DeliveryResponse(
        int size,
        List<DeliveryDto> deliveryDtoList
) {
}
