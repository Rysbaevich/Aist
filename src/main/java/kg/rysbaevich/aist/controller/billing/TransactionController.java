package kg.rysbaevich.aist.controller.billing;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.rysbaevich.aist.model.request.billing.NotificationRequest;
import kg.rysbaevich.aist.model.response.billing.Payment;
import kg.rysbaevich.aist.model.response.billing.PaymentResponse;
import kg.rysbaevich.aist.service.billing.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/transaction")
@Tag(name = "Төлөмдөр")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/payment")
    @Operation(summary = "Төлөм жүргүзүү үчүн", description = """
            MobileSDK үчүн paymentToken керектелет,
            paymentToken'ге эч нерсе берилбесе банк картасы аркылуу төлөм жүргүзүү керек.
            """)
    public ResponseEntity<?> pay(@RequestParam(required = false) String paymentToken,
                                 @RequestParam Long subscriptionTypeId,
                                 Principal principal) {
        PaymentResponse paymentResponse = transactionService.pay(principal.getName(), paymentToken, subscriptionTypeId);
        return ResponseEntity.ok().body(paymentResponse);
    }

    @Operation(summary = "Төлөмдүн статусун көрүү үчүн")
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<?> getStatus(@PathVariable String paymentId) {
        Payment.Status status = transactionService.checkStatus(paymentId);
        return ResponseEntity.ok().body(status);
    }

    @PostMapping("/notification")
    @Hidden
    public ResponseEntity<?> notification(@RequestBody NotificationRequest request) {
        transactionService.notificationAboutStatus(request);
        return ResponseEntity.ok().build();
    }
}
