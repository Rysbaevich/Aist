package kg.rysbaevich.aist.controller.customer;

import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.rysbaevich.aist.enums.Sex;
import kg.rysbaevich.aist.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Авторизация")
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Parameter(description = "Электрондук почтасы", example = "example@gmail.com")
            @RequestParam String email,
            @Parameter(description = "Кеминде 6 символдон туруш керек")
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String surname,
            @Parameter(description = "Туулган күнү", example = "2024-01-01")
            @RequestParam LocalDate dateOfBirth,
            @RequestParam Sex sex,
            @Parameter(description = "Телефон номери", example = "+996555001122")
            @RequestParam String phone) {

        customerService.registerUser(email, password, name, surname, dateOfBirth, sex, phone);
        return ResponseEntity.ok().body("Verification link send to " + email);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Parameter(description = "Электрондук почтасы", example = "example@gmail.com")
            @RequestParam String email,
            @RequestParam String password,
            @Parameter(description = "Телефон номери", example = "996555001122")
            @RequestParam(required = false) String phoneNumber) throws FirebaseAuthException {
        return ResponseEntity.ok(customerService.loginUser(email, password, phoneNumber));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String refreshToken) {
        return ResponseEntity.ok(customerService.refreshToken(refreshToken));
    }

    @PostMapping("/reset")
    public ResponseEntity<?> reset(@RequestParam String email) {
        customerService.resetPassword(email);
        return ResponseEntity.ok().build();
    }
}
