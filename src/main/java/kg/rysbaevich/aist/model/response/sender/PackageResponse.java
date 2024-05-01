package kg.rysbaevich.aist.model.response.sender;

import kg.rysbaevich.aist.enums.PackageType;

import java.math.BigDecimal;

public record PackageResponse(
        long id,
        String packageName,
        int height,
        int width,
        int length,
        PackageType type,
        BigDecimal weight) {}
