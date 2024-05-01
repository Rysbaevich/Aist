package kg.rysbaevich.aist.model.dto.customer;

import kg.rysbaevich.aist.enums.Sex;
import kg.rysbaevich.aist.model.entity.customer.Customer;

import java.time.LocalDate;

public record CustomerDto(
        String id,
        String phoneNumber,
        String name,
        String surname,
        LocalDate dob,
        String email,
        Sex sex
) {
    public CustomerDto(Customer customer) {
        this(
                customer.getId(),
                customer.getPhoneNumber(),
                customer.getName(),
                customer.getSurname(),
                customer.getDob(),
                customer.getEmail(),
                customer.getSex()
        );
    }

    public CustomerDto(String phoneNumber) {
        this(null, phoneNumber, null, null, null, null, null);
    }
}
