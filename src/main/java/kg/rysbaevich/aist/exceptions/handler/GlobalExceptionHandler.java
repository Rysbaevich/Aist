package kg.rysbaevich.aist.exceptions.handler;

import com.google.firebase.auth.FirebaseAuthException;
import kg.rysbaevich.aist.exceptions.NotFoundException;
import kg.rysbaevich.aist.exceptions.RegistrationException;
import kg.rysbaevich.aist.exceptions.VerificationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class, RegistrationException.class, VerificationException.class, IllegalArgumentException.class, FirebaseAuthException.class})
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleInternalException(Exception ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
