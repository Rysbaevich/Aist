package kg.rysbaevich.aist.service.packages.impl;

import kg.rysbaevich.aist.enums.PackageType;
import kg.rysbaevich.aist.enums.Transport;
import kg.rysbaevich.aist.model.dto.packages.PackageDto;
import kg.rysbaevich.aist.model.entity.packages.PackageEntity;
import kg.rysbaevich.aist.model.response.packages.PackageResponse;
import kg.rysbaevich.aist.repository.packages.PackageRepository;
import kg.rysbaevich.aist.service.packages.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;

    @Override
    public PackageResponse save(PackageDto packageDto) {
        PackageEntity packageEntity = new PackageEntity();
        packageEntity.setPackageName(packageDto.packageName());
        packageEntity.setLength(packageDto.length());
        packageEntity.setHeight(packageDto.height());
        packageEntity.setWeight(packageDto.weight());
        packageEntity.setType(packageDto.type());
        packageEntity.setWidth(packageDto.width());
        packageEntity.setForCar(packageDto.isForCar());
        packageEntity.setForPlane(packageDto.isForPlane());
        packageEntity.setForTruck(packageDto.isForTruck());
        packageEntity = packageRepository.save(packageEntity);
        return new PackageResponse(packageEntity);
    }

    @Override
    public List<PackageResponse> getAll() {
        List<PackageEntity> entities = packageRepository.findAll();

        List<PackageResponse> responses = new ArrayList<>();
        for (PackageEntity e : entities) {
            responses.add(new PackageResponse(e));
        }
        return responses;
    }

    @Override
    public List<PackageResponse> getAllByTransport(Transport transport) {
        List<PackageEntity> entities;
        if (transport == Transport.CAR) {
            entities = packageRepository.findAllByForCarTrue();
        } else if (transport == Transport.PLANE) {
            entities = packageRepository.findAllByForPlaneTrue();
        } else {
            entities = packageRepository.findAllByForTruckTrue();
        }


        List<PackageResponse> responses = new ArrayList<>();
        for (PackageEntity e : entities) {
            responses.add(new PackageResponse(e));
        }
        return responses;
    }

    @Override
    public List<PackageResponse> getAllByPackageType(PackageType packageType) {
        List<PackageEntity> entities = packageRepository.findAllByType(packageType);

        List<PackageResponse> responses = new ArrayList<>();
        for (PackageEntity e : entities) {
            responses.add(new PackageResponse(e));
        }
        return responses;
    }

    @Override
    public PackageResponse getById(Long id) {
        return new PackageResponse(packageRepository.findById(id).orElseThrow());
    }
}
