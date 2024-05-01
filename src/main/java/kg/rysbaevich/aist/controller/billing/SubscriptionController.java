package kg.rysbaevich.aist.controller.billing;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.rysbaevich.aist.model.dto.billing.SubscriptionDto;
import kg.rysbaevich.aist.service.billing.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/subscription")
@Tag(name = "Катталуулар")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    @Operation(summary = "Катталуулар",
            description = """
                    Колдонуучунун учурдагы жана келечектеги бардык катталуулары көрүнөт,
                    катталуунун башталгыч датасы менен ирээттелет.
                    """)
    public ResponseEntity<?> getSubscriptionByCustomer(Principal principal) {
        List<SubscriptionDto> dtoList = subscriptionService.getActiveSubscription(principal.getName());
        return ResponseEntity.ok(dtoList);
    }

}
