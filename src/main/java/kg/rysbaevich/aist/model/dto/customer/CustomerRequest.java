package kg.rysbaevich.aist.model.dto.customer;

import kg.rysbaevich.aist.enums.Sex;

import java.time.LocalDate;

public record CustomerRequest(
        String name,
        String surname,
        LocalDate dob,
        String email,
        Sex sex) {}
