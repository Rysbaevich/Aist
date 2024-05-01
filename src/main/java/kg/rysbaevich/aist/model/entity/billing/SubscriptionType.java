package kg.rysbaevich.aist.model.entity.billing;

import jakarta.persistence.*;
import kg.rysbaevich.aist.enums.Currency;
import kg.rysbaevich.aist.enums.SubscriptionName;
import kg.rysbaevich.aist.model.dto.billing.SubscriptionTypeDto;
import kg.rysbaevich.aist.model.entity.BaseEntity;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(schema = "billing")
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubscriptionName name;

    private int periodInMonth;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    public SubscriptionType(SubscriptionTypeDto subscriptionTypeDto) {
        this.setId(subscriptionTypeDto.id());
        this.setName(subscriptionTypeDto.name());
        this.setPeriodInMonth(subscriptionTypeDto.periodInMonth());
        this.setPrice(subscriptionTypeDto.price());
        this.setCurrency(subscriptionTypeDto.currency());
    }
}
