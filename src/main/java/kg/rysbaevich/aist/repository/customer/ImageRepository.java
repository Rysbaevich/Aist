package kg.rysbaevich.aist.repository.customer;

import kg.rysbaevich.aist.model.entity.customer.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByCustomerId(String customerId);
}
