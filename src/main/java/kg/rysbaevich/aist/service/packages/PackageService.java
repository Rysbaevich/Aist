package kg.rysbaevich.aist.service.packages;

import kg.rysbaevich.aist.enums.PackageType;
import kg.rysbaevich.aist.enums.Transport;
import kg.rysbaevich.aist.model.dto.packages.PackageDto;
import kg.rysbaevich.aist.model.response.packages.PackageResponse;

import java.util.List;

public interface PackageService {

    PackageResponse save(PackageDto packageDto);
    List<PackageResponse> getAll();
    List<PackageResponse> getAllByTransport(Transport transport);

    List<PackageResponse> getAllByPackageType(PackageType packageType);

    PackageResponse getById(Long id);
}
