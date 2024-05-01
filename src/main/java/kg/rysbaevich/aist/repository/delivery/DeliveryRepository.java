package kg.rysbaevich.aist.repository.delivery;

import kg.rysbaevich.aist.model.entity.delivery.Delivery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    @Query("""
                SELECT d FROM Delivery d
                WHERE LOWER(d.fromLocation) LIKE %:fromLocation%
                AND LOWER(d.toLocation) LIKE %:toLocation%
                AND d.departureDate = :departureDate
                AND d.arrivalDate = :arrivalDate
            """)
    List<Delivery> findAllByToLocationAndFromLocationAndDepartureDate(@Param("fromLocation") String fromLocation,
                                                                      @Param("toLocation") String toLocation,
                                                                      @Param("departureDate") LocalDate departureDate,
                                                                      @Param("arrivalDate") LocalDate arrivalDate,
                                                                      Pageable pageable);

    List<Delivery> findByCustomerId(String customerId, Pageable pageable);
}