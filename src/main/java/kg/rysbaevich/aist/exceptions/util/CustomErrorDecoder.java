package kg.rysbaevich.aist.exceptions.util;

import feign.Response;
import feign.codec.ErrorDecoder;
import kg.rysbaevich.aist.exceptions.VerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        if (methodKey.contains("FirebaseLoginRequest") && response.status() == 400) {
            return new VerificationException("Invalid username or password");
        }

        if (methodKey.contains("FirebaseRefreshRequest") && response.status() == 400) {
            return new VerificationException("Invalid refresh token");
        }

        return null;
    }
}
