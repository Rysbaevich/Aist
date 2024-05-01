package kg.rysbaevich.aist.repository.customer;


import kg.rysbaevich.aist.model.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
