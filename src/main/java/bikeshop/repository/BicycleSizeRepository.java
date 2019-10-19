package bikeshop.repository;

import bikeshop.domain.entities.BicycleSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BicycleSizeRepository extends JpaRepository<BicycleSize, String> {
}
