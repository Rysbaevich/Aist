package kg.rysbaevich.aist.service.customer;


public interface EmailVerificationService {
    void sendVerificationEmail(String email, String name, String token);
    void sendResetPasswordEmail(String email, String link);
}
