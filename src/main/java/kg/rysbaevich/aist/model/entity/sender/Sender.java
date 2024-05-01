package kg.rysbaevich.aist.model.entity.sender;

import jakarta.persistence.*;
import kg.rysbaevich.aist.model.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(schema = "sender")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sender extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long packageId;
    private String customerId;
    private boolean active = true;
    private String description;
    private String fromLocation;
    private String toLocation;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
}
