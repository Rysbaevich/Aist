package kg.rysbaevich.aist.service.customer;

import kg.rysbaevich.aist.model.dto.customer.ImageDto;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageDto storePhoto(String customerId, MultipartFile file);
    ImageDto getPhoto(String customerId);
}
