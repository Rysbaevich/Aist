package kg.rysbaevich.aist.repository.packages;

import kg.rysbaevich.aist.enums.PackageType;
import kg.rysbaevich.aist.model.entity.packages.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {

    @Query(value = """
            SELECT * FROM package
            WHERE is_for_car = true
            """, nativeQuery = true)
    List<PackageEntity> findAllByForCarTrue();

    @Query(value = """
            SELECT * FROM package
            WHERE is_for_plane = true
            """, nativeQuery = true)
    List<PackageEntity> findAllByForPlaneTrue();

    @Query(value = """
            SELECT * FROM package
            WHERE is_for_truck = true
            """, nativeQuery = true)
    List<PackageEntity> findAllByForTruckTrue();

    List<PackageEntity> findAllByType(PackageType type);
}
