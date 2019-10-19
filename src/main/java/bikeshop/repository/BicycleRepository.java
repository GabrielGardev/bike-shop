package bikeshop.repository;

import bikeshop.domain.entities.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BicycleRepository extends JpaRepository<Bicycle, String> {
}
