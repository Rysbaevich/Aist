package kg.rysbaevich.aist.controller.delivery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.rysbaevich.aist.model.request.delivery.DeliveryRequest;
import kg.rysbaevich.aist.enums.Transport;
import kg.rysbaevich.aist.service.delivery.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/v1/delivery")
@Tag(name = "Жеткирүүчү")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("/CAR")
    @PreAuthorize(value = "hasAuthority('DELIVERY_CAR')")
    public ResponseEntity<?> saveCar(@RequestBody DeliveryRequest deliveryRequest,
                                     Principal principal) {
        return ResponseEntity.ok(deliveryService.save(Transport.CAR, deliveryRequest, principal.getName()));
    }

    @PostMapping("/TRUCK")
    @PreAuthorize(value = "hasAuthority('DELIVERY_TRUCK')")
    public ResponseEntity<?> saveTruck(@RequestBody DeliveryRequest deliveryRequest,
                                       Principal principal) {
        return ResponseEntity.ok(deliveryService.save(Transport.TRUCK, deliveryRequest, principal.getName()));
    }

    @PostMapping("/PLANE")
    @PreAuthorize(value = "hasAnyAuthority('DELIVERY_PLANE')")
    public ResponseEntity<?> savePlane(@RequestBody DeliveryRequest deliveryRequest,
                                       Principal principal) {
        return ResponseEntity.ok(deliveryService.save(Transport.PLANE, deliveryRequest, principal.getName()));
    }

    @GetMapping("/all/{offset}/{pageSize}")
    @Operation(summary = "Бардык жеткирүүлөр",
            description = """
                    Бардык мүмкүн болгон жеткирүүлөрдү алуу үчүн
                    """)
    public ResponseEntity<?> getAllByFromToAndWhen(@RequestParam String fromLocation,
                                                   @RequestParam String toLocation,
                                                   @RequestParam LocalDate departureDate,
                                                   @RequestParam LocalDate arrivalDate,
                                                   @PathVariable int offset,
                                                   @PathVariable int pageSize) {
        return ResponseEntity.ok(deliveryService.findAllByFromToAndWhen(fromLocation, toLocation, departureDate, arrivalDate, offset, pageSize));
    }

    @GetMapping("/customer/{offset}/{pageSize}")
    public ResponseEntity<?> getByCustomerId(@PathVariable int offset,
                                             @PathVariable int pageSize,
                                             Principal principal) {
        return ResponseEntity.ok(deliveryService.findByCustomerId(principal.getName(), offset, pageSize));
    }
}
