package kg.rysbaevich.aist.service.delivery.impl;

import kg.rysbaevich.aist.model.dto.delivery.DeliveryDto;
import kg.rysbaevich.aist.model.request.delivery.DeliveryRequest;
import kg.rysbaevich.aist.model.response.delivery.DeliveryResponse;
import kg.rysbaevich.aist.model.entity.delivery.Delivery;
import kg.rysbaevich.aist.enums.Transport;
import kg.rysbaevich.aist.repository.delivery.DeliveryRepository;
import kg.rysbaevich.aist.service.delivery.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Override
    public Long save(Transport transport, DeliveryRequest deliveryRequest, String customerId) {

        Delivery delivery = new Delivery();
        delivery.setCustomerId(customerId);
        delivery.setPackageId(deliveryRequest.packageId());
        delivery.setFromLocation(deliveryRequest.fromLocation());
        delivery.setToLocation(deliveryRequest.toLocation());
        delivery.setArrivalDate(deliveryRequest.arrivalDate());
        delivery.setDepartureDate(deliveryRequest.departureDate());
        delivery.setDescription(deliveryRequest.description());

        delivery = deliveryRepository.save(delivery);
        return delivery.getId();
    }

    @Override
    public DeliveryResponse findAllByFromToAndWhen(String fromLocation, String toLocation, LocalDate departureDate, LocalDate arrivalDate, int pageNumber, int pageSize) {
        List<Delivery> list = deliveryRepository
                .findAllByToLocationAndFromLocationAndDepartureDate(fromLocation.toLowerCase(),
                        toLocation.toLowerCase(), departureDate, arrivalDate, PageRequest.of(pageNumber, pageSize).withSort(Sort.by("createdAt")));

        List<DeliveryDto> responseList = new ArrayList<>();
        for (Delivery d : list) {
            responseList.add(new DeliveryDto(d));
        }

        return new DeliveryResponse(responseList.size(), responseList);
    }

    @Override
    public DeliveryResponse findByCustomerId(String customerId, int pageNumber, int pageSize) {
        List<Delivery> list = deliveryRepository
                .findByCustomerId(customerId, PageRequest.of(pageNumber, pageSize).withSort(Sort.by("createdAt")));

        List<DeliveryDto> responseList = new ArrayList<>();
        for (Delivery d : list) {
            responseList.add(new DeliveryDto(d));
        }

        return new DeliveryResponse(responseList.size(), responseList);
    }

}
