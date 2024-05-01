package kg.rysbaevich.aist.controller.billing;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.rysbaevich.aist.enums.SubscriptionName;
import kg.rysbaevich.aist.model.dto.billing.SubscriptionTypeDto;
import kg.rysbaevich.aist.service.billing.SubscriptionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscription/type")
@Tag(name = "Катталуунун түрлөрү")
@RequiredArgsConstructor
public class SubscriptionTypeController {

    private final SubscriptionTypeService subscriptionTypeService;

    @GetMapping("/all")
    @Operation(summary = "Бардык катталуулардын түрүн көрүү үчүн")
    public ResponseEntity<List<SubscriptionTypeDto>> getAllTypes() {
        return ResponseEntity.ok(subscriptionTypeService.getAll());
    }

    @GetMapping("/{name}")
    @Operation(summary = "Белгилүү бир катталуунун бааларын көрүү үчүн")
    public ResponseEntity<List<SubscriptionTypeDto>> getAllByType(@PathVariable SubscriptionName name) {
        return ResponseEntity.ok(subscriptionTypeService.getAllByType(name));
    }

}
