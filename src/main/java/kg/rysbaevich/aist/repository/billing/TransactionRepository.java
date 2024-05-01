package kg.rysbaevich.aist.repository.billing;

import kg.rysbaevich.aist.model.entity.billing.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
