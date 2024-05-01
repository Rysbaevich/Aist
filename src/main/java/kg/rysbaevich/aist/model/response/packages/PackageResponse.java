package kg.rysbaevich.aist.model.response.packages;

import kg.rysbaevich.aist.enums.PackageType;
import kg.rysbaevich.aist.model.entity.packages.PackageEntity;

import java.math.BigDecimal;

public record PackageResponse(
        long id,
        String packageName,
        int height,
        int width,
        int length,
        PackageType type,
        BigDecimal weight) {
    public PackageResponse(PackageEntity packageEntity) {
        this(
                packageEntity.getId(), packageEntity.getPackageName(),
                packageEntity.getHeight(), packageEntity.getWidth(),
                packageEntity.getLength(), packageEntity.getType(), packageEntity.getWeight()
        );
    }
}
