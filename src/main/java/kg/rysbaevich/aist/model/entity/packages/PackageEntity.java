package kg.rysbaevich.aist.model.entity.packages;

import jakarta.persistence.*;
import kg.rysbaevich.aist.enums.PackageType;
import kg.rysbaevich.aist.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;


@Entity
@Table(name = "package", schema = "package")
@Getter @Setter
public class PackageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String packageName;

    private int height;

    private int width;

    private int length;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PackageType type;

    @Column(scale = 2)
    private BigDecimal weight;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isForPlane;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isForCar;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isForTruck;
}
