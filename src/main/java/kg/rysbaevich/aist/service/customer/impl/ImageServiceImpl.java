package kg.rysbaevich.aist.service.customer.impl;

import kg.rysbaevich.aist.exceptions.StorageException;
import kg.rysbaevich.aist.model.dto.customer.ImageDto;
import kg.rysbaevich.aist.model.entity.customer.Customer;
import kg.rysbaevich.aist.model.entity.customer.Image;
import kg.rysbaevich.aist.repository.customer.ImageRepository;
import kg.rysbaevich.aist.service.customer.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public ImageDto storePhoto(String customerId, MultipartFile file) {
        try {
            var optionalImage = imageRepository.findByCustomerId(customerId);
            Image image;

            if (optionalImage.isEmpty()) {
                Customer customer = new Customer();
                customer.setId(customerId);

                image = new Image();
                image.setCustomer(customer);
            } else {
                image = optionalImage.get();
            }

            byte[] byteObjects = new byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            image.setPhoto(byteObjects);

            image = imageRepository.save(image);
            return new ImageDto(image);
        } catch (IOException e) {
            log.error("Image storage error", e);
            throw new StorageException("Image storage error", e);
        }
    }

    @Override
    @Transactional
    public ImageDto getPhoto(String customerId) {
        Image image;
        var optionalImage = imageRepository.findByCustomerId(customerId);
        image = optionalImage.orElseGet(() -> imageRepository.findById(1L).orElseThrow());
        return new ImageDto(image);
    }
}
