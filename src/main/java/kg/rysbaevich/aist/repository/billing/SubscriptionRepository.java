package kg.rysbaevich.aist.repository.billing;

import kg.rysbaevich.aist.model.entity.billing.Subscription;
import kg.rysbaevich.aist.repository.customer.CustomerIdProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(value = """
            SELECT * FROM subscription
            WHERE customer_id = :customerId
            AND is_active = true
            ORDER BY start_date
        """, nativeQuery = true)
    List<Subscription> findAllActiveByCustomerIdNow(String customerId);

    @Query(value = """
            SELECT * FROM subscription
            WHERE billing.public.subscription.customer_id = :customerId
            AND type_id = :subscriptionTypeId
            AND end_date > now()
            ORDER BY end_date DESC
            LIMIT 1
        """, nativeQuery = true)
    Optional<Subscription> findLastSubscriptionByCustomerIdAndTypeId(String customerId, long subscriptionTypeId);


    @Query(value = """
            SELECT DISTINCT customer_id as customerId FROM subscription
            WHERE is_active = TRUE
              AND end_date < NOW()
        """, nativeQuery = true)
    Set<CustomerIdProjection> findAllActiveAndEndDateIsBeforeNow();

    @Query(value = """
            SELECT * FROM subscription
            WHERE customer_id = :customerId
            AND is_active = TRUE
              AND end_date < NOW()
        """, nativeQuery = true)
    List<Subscription> findAllByCustomerIdActiveAndEndDateIsBeforeNow(String customerId);

}
