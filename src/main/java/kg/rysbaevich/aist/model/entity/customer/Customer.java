package kg.rysbaevich.aist.model.entity.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import kg.rysbaevich.aist.enums.Sex;
import kg.rysbaevich.aist.model.dto.customer.CustomerDto;
import kg.rysbaevich.aist.model.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "customer", schema = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {
    @Id
    private String id;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    private String name;
    private String surname;
    private LocalDate dob;
    @Column(name = "email", unique = true)
    @Email
    private String email;
    @Enumerated(EnumType.STRING)
    private Sex sex;

    public Customer(CustomerDto customerDto) {
        this.id = customerDto.id();
        this.phoneNumber = customerDto.phoneNumber();
        this.name = customerDto.name();
        this.surname = customerDto.surname();
        this.dob = customerDto.dob();
        this.email = customerDto.email();
        this.sex = customerDto.sex();
    }
}
