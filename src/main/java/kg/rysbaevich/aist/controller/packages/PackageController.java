package kg.rysbaevich.aist.controller.packages;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.rysbaevich.aist.enums.PackageType;
import kg.rysbaevich.aist.enums.Transport;
import kg.rysbaevich.aist.model.dto.packages.PackageDto;
import kg.rysbaevich.aist.model.response.packages.PackageResponse;
import kg.rysbaevich.aist.service.packages.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@Tag(name = "Аманат")
@RequestMapping("/api/v1/package")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    @Hidden
    @PostMapping
    @PreAuthorize(value = "hasAuthority('admin')")
    public ResponseEntity<PackageResponse> createPackage(
            @RequestParam String packageName,
            @RequestParam(required = false, defaultValue = "0") int height,
            @RequestParam(required = false, defaultValue = "0") int width,
            @RequestParam(required = false, defaultValue = "0") int length,
            @RequestParam PackageType type,
            @RequestParam(required = false, defaultValue = "0.00") BigDecimal weight,
            @RequestParam(required = false, defaultValue = "false") boolean isForPlane,
            @RequestParam(required = false, defaultValue = "false") boolean isForCar,
            @RequestParam(required = false, defaultValue = "false") boolean isForTruck) {
        return ResponseEntity.ok(packageService.save(
                new PackageDto(
                        packageName,
                        height,
                        width,
                        length,
                        type,
                        weight,
                        isForPlane,
                        isForCar,
                        isForTruck)
                )
        );
    }

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam Long id) {
        return ResponseEntity.ok(packageService.getById(id));
    }

    @GetMapping("/transport")
    public ResponseEntity<?> findAllByTransport(@RequestParam Transport transport) {
        return ResponseEntity.ok(packageService.getAllByTransport(transport));
    }

    @GetMapping("/type")
    public ResponseEntity<?> findAllByType(@RequestParam PackageType packageType) {
        return ResponseEntity.ok(packageService.getAllByPackageType(packageType));
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(packageService.getAll());
    }
}
