package kg.rysbaevich.aist.model.entity.customer;

import jakarta.persistence.*;
import kg.rysbaevich.aist.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "customer")
@Getter @Setter
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Customer customer;

    @Lob
    private byte[] photo;
}
