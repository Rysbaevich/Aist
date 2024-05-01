package kg.rysbaevich.aist.model.entity.billing;

import jakarta.persistence.*;
import kg.rysbaevich.aist.model.response.billing.Payment;
import kg.rysbaevich.aist.util.PaymentJsonConvertor;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static kg.rysbaevich.aist.model.response.billing.Payment.Status;

@Entity
@Table(schema = "billing")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String customerId;

    private UUID paymentId;

    @Enumerated(EnumType.STRING)
    Status status;

    @Column(columnDefinition = "varchar")
    @Convert(converter = PaymentJsonConvertor.class)
    private Payment payment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
