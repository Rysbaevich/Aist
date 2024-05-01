package kg.rysbaevich.aist.model.entity.billing;

import jakarta.persistence.*;
import kg.rysbaevich.aist.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(schema = "billing")
@Getter @Setter
@ToString
public class Subscription extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerId;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private SubscriptionType type;

    @OneToOne
    private Transaction transaction;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean isActive;
}
