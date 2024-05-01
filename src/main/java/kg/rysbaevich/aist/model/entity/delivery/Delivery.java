package kg.rysbaevich.aist.model.entity.delivery;

import jakarta.persistence.*;
import kg.rysbaevich.aist.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(schema = "delivery")
@Getter @Setter
public class Delivery extends BaseEntity {

    @Id
    @SequenceGenerator(name = "delivery_gen", sequenceName = "delivery_seq",
            allocationSize = 1, initialValue = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_gen")
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
