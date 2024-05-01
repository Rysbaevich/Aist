package kg.rysbaevich.aist.service.customer.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import kg.rysbaevich.aist.enums.Sex;
import kg.rysbaevich.aist.exceptions.NotFoundException;
import kg.rysbaevich.aist.exceptions.RegistrationException;
import kg.rysbaevich.aist.exceptions.VerificationException;
import kg.rysbaevich.aist.feign.FirebaseFeign;
import kg.rysbaevich.aist.model.dto.customer.CustomerDto;
import kg.rysbaevich.aist.model.dto.customer.CustomerRequest;
import kg.rysbaevich.aist.model.dto.customer.ImageDto;
import kg.rysbaevich.aist.model.entity.customer.Customer;
import kg.rysbaevich.aist.model.request.customer.FirebaseLoginRequest;
import kg.rysbaevich.aist.model.request.customer.FirebaseRefreshRequest;
import kg.rysbaevich.aist.model.response.customer.LoginResponse;
import kg.rysbaevich.aist.repository.customer.CustomerRepository;
import kg.rysbaevich.aist.service.customer.CustomerService;
import kg.rysbaevich.aist.service.customer.EmailVerificationService;
import kg.rysbaevich.aist.service.customer.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final EmailVerificationService emailVerificationService;
    private final ImageService imageService;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFeign firebaseFeign;

    @Override
    @Transactional
    public void registerUser(String email, String password, String name, String surname, LocalDate dateOfBirth, Sex sex, String phone) {
        try {
            firebaseAuth.getUserByEmail(email);
            throw new RegistrationException("User already exists");
        } catch (FirebaseAuthException e) {
            log.info("User not found, firebase answer: {}", e.getMessage());
        }



        if (password.length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
        String verificationLink;
        UserRecord.CreateRequest request = new UserRecord.CreateRequest();
        request.setEmail(email);
        request.setPassword(password);
        request.setEmailVerified(false);
        request.setPhoneNumber(phone);
        try {
            var firebaseCustomer = firebaseAuth.createUser(request);
            Customer customer = new Customer();
            customer.setId(firebaseCustomer.getUid());
            customer.setEmail(email);
            customer.setPhoneNumber(phone);
            customer.setName(name);
            customer.setSurname(surname);
            customer.setDob(dateOfBirth);
            customer.setSex(sex);
            customerRepository.save(customer);

            verificationLink = firebaseAuth.generateEmailVerificationLink(email);
        } catch (FirebaseAuthException e) {
            throw new RegistrationException(e.getMessage());
        }

        emailVerificationService.sendVerificationEmail(email, "Nurdoolot", verificationLink);
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        var firebaseRefreshResponse = firebaseFeign.refresh(new FirebaseRefreshRequest("refresh_token", refreshToken));
        return new LoginResponse(firebaseRefreshResponse.access_token(), firebaseRefreshResponse.refresh_token(), firebaseRefreshResponse.user_id());
    }

    @Override
    public void resetPassword(String email) {
        try {
            String resetLink = firebaseAuth.generatePasswordResetLink(email);
            emailVerificationService.sendResetPasswordEmail(email, resetLink);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException("Error while resetting password", e);
        }
    }

    @Override
    public LoginResponse loginUser(String email, String password, String phone) throws FirebaseAuthException {
        var user = firebaseAuth.getUserByEmail(email);
        if (user == null) {
            throw new NotFoundException("User with email not found: " + email);
        }

        if (!user.isEmailVerified()) {
            throw new VerificationException("Email is not verified");
        }

        var firebaseResponse = firebaseFeign.login(new FirebaseLoginRequest(email, password, true));
        return new LoginResponse(firebaseResponse.idToken(), firebaseResponse.refreshToken(), firebaseResponse.localId());
    }

    @Override
    public void deleteUser(String userId) throws FirebaseAuthException {
        firebaseAuth.deleteUser(userId);
    }

    @Override
    public CustomerDto findUserByPhone(String phone) {
        Optional<Customer> optionalUser = customerRepository.findByPhoneNumber(phone);
        return new CustomerDto(optionalUser.orElseThrow());
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = customerRepository.save(new Customer(customerDto));
        return new CustomerDto(customer);
    }

    @Override
    public ImageDto changePhoto(String customerId, MultipartFile multipartFile) {
        customerRepository.findById(customerId).orElseThrow();
        return imageService.storePhoto(customerId, multipartFile);
    }

    @Override
    public CustomerDto updateCustomer(String customerId, CustomerRequest customerRequest) {
        log.info("Updating customer by phone {}: {}", customerId, customerRequest);
        var customer = customerRepository.findById(customerId).orElseThrow();
        customer.setName(customerRequest.name());
        customer.setSurname(customerRequest.surname());
        customer.setDob(customerRequest.dob());
        customer.setSex(customerRequest.sex());
        customer.setEmail(customerRequest.email());
        customer = customerRepository.save(customer);
        return new CustomerDto(customer);
    }

    @Override
    public byte[] getPhoto(String customerId) {
        customerRepository.findById(customerId).orElseThrow();
        ImageDto imageDto = imageService.getPhoto(customerId);
        return imageDto.photo();
    }

    @Override
    public CustomerDto findCustomerById(String customerId) {
        var customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new NotFoundException("Customer not found");
        }
        return new CustomerDto(customer.get());
    }
}
