package kg.rysbaevich.aist.controller.sender;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.rysbaevich.aist.model.request.sender.SenderRequest;
import kg.rysbaevich.aist.enums.PackageType;
import kg.rysbaevich.aist.service.sender.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/v1/sender")
@Tag(name = "Жөнөтүүчү")
@RequiredArgsConstructor
public class SenderController {

    private final SenderService senderService;

    @PostMapping("/ENVELOPE")
    @PreAuthorize("hasAuthority('SENDER_ENVELOPE')")
    public ResponseEntity<?> saveEnvelope(@RequestBody SenderRequest senderRequest,
                                  Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken().getTokenValue();
        String customerId = jwtAuthenticationToken.getName();
        return ResponseEntity.ok(senderService.save(PackageType.ENVELOPE, senderRequest, customerId, token));
    }

    @PostMapping("/BOX")
    @PreAuthorize("hasAuthority('SENDER_BOX')")
    public ResponseEntity<?> saveBox(@RequestBody SenderRequest senderRequest,
                                  Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken().getTokenValue();
        String customerId = jwtAuthenticationToken.getName();
        return ResponseEntity.ok(senderService.save(PackageType.BOX, senderRequest, customerId, token));
    }

    @PostMapping("/SUITCASE")
    @PreAuthorize("hasAuthority('SENDER_SUITCASE')")
    public ResponseEntity<?> saveSuitcase(@RequestBody SenderRequest senderRequest,
                                  Authentication authentication) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken().getTokenValue();
        String customerId = jwtAuthenticationToken.getName();
        return ResponseEntity.ok(senderService.save(PackageType.SUITCASE, senderRequest, customerId, token));
    }

    @GetMapping("/all/{offset}/{pageSize}")
    public ResponseEntity<?> getAllByFromToAndWhen(@RequestParam String fromLocation,
                                                   @RequestParam String toLocation,
                                                   @RequestParam LocalDate when,
                                                   @PathVariable int offset,
                                                   @PathVariable int pageSize) {
        return ResponseEntity.ok(senderService.findAllByFromToAndWhen(fromLocation, toLocation, when, offset, pageSize));
    }

    @GetMapping("/customer/{offset}/{pageSize}")
    public ResponseEntity<?> getByCustomerId(@PathVariable int offset,
                                             @PathVariable int pageSize,
                                             Principal principal) {
        return ResponseEntity.ok(senderService.findByCustomerId(principal.getName(), offset, pageSize));
    }
}
