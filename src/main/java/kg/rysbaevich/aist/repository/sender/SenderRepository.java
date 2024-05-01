package kg.rysbaevich.aist.repository.sender;

import kg.rysbaevich.aist.model.entity.sender.Sender;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SenderRepository extends JpaRepository<Sender, Long> {
    @Query("""
                SELECT s FROM Sender s
                WHERE LOWER(s.fromLocation) LIKE %:fromLocation%
                AND LOWER(s.toLocation) LIKE %:toLocation%
                AND s.departureDate = :date
            """)
    List<Sender> findAllByToLocationAndFromLocationAndDepartureDate(@Param("fromLocation") String fromLocation,
                                                                    @Param("toLocation") String toLocation,
                                                                    @Param("date") LocalDate date,
                                                                    Pageable pageable);

    List<Sender> findByCustomerId(String customerId, Pageable pageable);
}