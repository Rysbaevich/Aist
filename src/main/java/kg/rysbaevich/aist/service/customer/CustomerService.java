package kg.rysbaevich.aist.service.customer;

import com.google.firebase.auth.FirebaseAuthException;
import kg.rysbaevich.aist.enums.Sex;
import kg.rysbaevich.aist.model.dto.customer.CustomerDto;
import kg.rysbaevich.aist.model.dto.customer.CustomerRequest;
import kg.rysbaevich.aist.model.dto.customer.ImageDto;
import kg.rysbaevich.aist.model.response.customer.LoginResponse;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface CustomerService {
    CustomerDto findUserByPhone(String phoneNumber);
    CustomerDto save(CustomerDto customerDto);
    ImageDto changePhoto(String customerId, MultipartFile multipartFile);
    CustomerDto updateCustomer(String customerId, CustomerRequest customerRequest);
    byte[] getPhoto(String customerId);
    CustomerDto findCustomerById(String customerId);
    LoginResponse loginUser(String email, String password, String phoneNumber) throws FirebaseAuthException;
    void deleteUser(String phoneNumber) throws FirebaseAuthException;
    void registerUser(String email, String password, String name, String surname, LocalDate dateOfBirth, Sex sex, String phone);
    LoginResponse refreshToken(String refreshToken);
    void resetPassword(String email);
}
