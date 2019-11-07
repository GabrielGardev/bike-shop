package bikeshop.repository;

import bikeshop.domain.entities.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BicycleRepository extends JpaRepository<Bicycle, String> {
    List<Bicycle> findAllByCategoryName(String name);

    Optional<Bicycle> findByMakeAndModelAndColor(String make, String model, String color);
}
