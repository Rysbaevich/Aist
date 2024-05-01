package kg.rysbaevich.aist.feign;

import kg.rysbaevich.aist.config.FeignConfig;
import kg.rysbaevich.aist.model.request.billing.PaymentRequest;
import kg.rysbaevich.aist.model.request.billing.PaymentRequestByCard;
import kg.rysbaevich.aist.model.response.billing.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "Yookassa", url = "https://api.yookassa.ru/v3", configuration = FeignConfig.class)
public interface Yookassa {

    @PostMapping("/payments")
    Payment payments(
            @RequestHeader("Idempotence-Key") String idempotenceKey,
            @RequestBody PaymentRequest paymentRequest);

    @PostMapping("/payments")
    Payment paymentsByCard(
            @RequestHeader("Idempotence-Key") String idempotenceKey,
            @RequestBody PaymentRequestByCard paymentRequest);

    @GetMapping("/payments/{paymentId}")
    Payment getPaymentStatus(@PathVariable String paymentId);

}
