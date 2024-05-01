package kg.rysbaevich.aist.service.sender;

import kg.rysbaevich.aist.model.request.sender.SenderRequest;
import kg.rysbaevich.aist.model.response.sender.SenderResponse;
import kg.rysbaevich.aist.enums.PackageType;

import java.time.LocalDate;

public interface SenderService {
    Long save(PackageType packageType, SenderRequest senderRequest, String customerId, String token);
    SenderResponse findAllByFromToAndWhen(String fromLocation, String toLocation, LocalDate date, int pageNumber, int pageSize);

    SenderResponse findByCustomerId(String customerId, int pageNumber, int pageSize);
}
