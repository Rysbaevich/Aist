package kg.rysbaevich.aist.model.dto.packages;

import kg.rysbaevich.aist.enums.PackageType;

import java.math.BigDecimal;

public record PackageDto(
        String packageName,
        int height,
        int width,
        int length,
        PackageType type,
        BigDecimal weight,
        boolean isForPlane,
        boolean isForCar,
        boolean isForTruck
) {
}
