package kg.rysbaevich.aist.controller.customer;

import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.rysbaevich.aist.model.dto.customer.CustomerRequest;
import kg.rysbaevich.aist.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/customer")
@Tag(name = "Профиль")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PutMapping
    @Operation(summary = "Жеке маалыматтарды толуктоо үчүн",
            description = "Эгер кайсыл бир параметр гана жаңыра турган болсо жаңырбай турган параметрди да кошо толтуруш керек")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerRequest customerRequest,
                                            Principal principal) {
        var customerDto = customerService.updateCustomer(principal.getName(), customerRequest);
        return ResponseEntity.ok(customerDto);
    }

    @PutMapping("/photo")
    @Operation(summary = "Сүрөт сактоо үчүн", description = """
            Мисал катары:
            curl --location --request PUT 'http://localhost:8080/api/v1/customer/photo/{customerId}' \\
            --form 'file=@"/C:/Users/Rnurd/OneDrive/Изображения/Wedding/FX1A7207_1_11zon.jpg"'
            """)
    public ResponseEntity<?> updateCustomersPhoto(@RequestParam("file") MultipartFile file,
                                                  Principal principal) {

        var imageDto = customerService.changePhoto(principal.getName(), file);

        byte[] responseFile = imageDto.photo();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(responseFile.length);
        headers.set("Content-Disposition", "attachment; filename=photo.png");

        return ResponseEntity.ok().headers(headers).body(responseFile);
    }

    @GetMapping("/photo")
    @Operation(summary = "Сүрөттү жүктөп алуу үчүн", description = """
            byte[] түрүндө, PNG форматта сүрөттү кайтарат
            """)
    public ResponseEntity<?> getCustomersPhoto(Principal principal) {
        byte[] file = customerService.getPhoto(principal.getName());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(file.length);
        headers.set("Content-Disposition", "attachment; filename=photo.png");

        return ResponseEntity.ok().headers(headers).body(file);
    }

    @GetMapping()
    @Operation(summary = "Колдонуучунун жеке маалыматтарын алуу үчүн")
    public ResponseEntity<?> getCustomerById(Principal principal) {
        return ResponseEntity.ok(customerService.findCustomerById(principal.getName()));
    }

    @DeleteMapping()
    @Operation(summary = "Колдонуучунун жеке маалыматтарын өчүрүү үчүн")
    public ResponseEntity<?> delete(Principal principal) throws FirebaseAuthException {
        customerService.deleteUser(principal.getName());
        return ResponseEntity.ok().build();
    }
}
