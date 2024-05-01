package kg.rysbaevich.aist.repository.billing;

import kg.rysbaevich.aist.enums.SubscriptionName;
import kg.rysbaevich.aist.model.entity.billing.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType, Long> {
    List<SubscriptionType> findAllByName(SubscriptionName name);
    List<SubscriptionType> findDistinctByNameNotIn(Set<SubscriptionName> names);
}
