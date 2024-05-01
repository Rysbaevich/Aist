package kg.rysbaevich.aist.service.sender.impl;

import kg.rysbaevich.aist.model.dto.sender.SenderDto;
import kg.rysbaevich.aist.model.request.sender.SenderRequest;
import kg.rysbaevich.aist.model.response.sender.SenderResponse;
import kg.rysbaevich.aist.model.entity.sender.Sender;
import kg.rysbaevich.aist.enums.PackageType;
import kg.rysbaevich.aist.repository.sender.SenderRepository;
import kg.rysbaevich.aist.service.packages.PackageService;
import kg.rysbaevich.aist.service.sender.SenderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SenderServiceImpl implements SenderService {

    private final SenderRepository senderRepository;
    private final PackageService packageService;

    @Override
    public Long save(PackageType packageType, SenderRequest senderRequest, String customerId, String token) {

        var packageResponse = packageService.getById(senderRequest.packageId());

        if (packageResponse.type() != packageType) {
            throw new RuntimeException("Incorrect packageType");
        }

        Sender sender = new Sender();
        sender.setCustomerId(customerId);
        sender.setPackageId(senderRequest.packageId());
        sender.setFromLocation(senderRequest.fromLocation());
        sender.setToLocation(senderRequest.toLocation());
        sender.setArrivalDate(senderRequest.arrivalDate());
        sender.setDepartureDate(senderRequest.departureDate());
        sender.setDescription(senderRequest.description());

        sender = senderRepository.save(sender);
        return sender.getId();
    }

    @Override
    public SenderResponse findAllByFromToAndWhen(String fromLocation, String toLocation, LocalDate date, int pageNumber, int pageSize) {
        List<Sender> list = senderRepository
                .findAllByToLocationAndFromLocationAndDepartureDate(fromLocation.toLowerCase(),
                        toLocation.toLowerCase(), date, PageRequest.of(pageNumber, pageSize).withSort(Sort.by("createdAt")));

        List<SenderDto> responseList = new ArrayList<>();
        for (Sender s : list) {
            responseList.add(new SenderDto(s));
        }

        return new SenderResponse(responseList.size(), responseList);
    }

    @Override
    public SenderResponse findByCustomerId(String customerId, int pageNumber, int pageSize) {
        List<Sender> list = senderRepository.findByCustomerId(customerId, PageRequest.of(pageNumber, pageSize).withSort(Sort.by("createdAt")));

        List<SenderDto> responseList = new ArrayList<>();
        for (Sender s : list) {
            responseList.add(new SenderDto(s));
        }

        return new SenderResponse(responseList.size(), responseList);
    }

}
