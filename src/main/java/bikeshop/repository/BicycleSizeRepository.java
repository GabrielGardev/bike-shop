package bikeshop.repository;

import bikeshop.domain.entities.BicycleSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BicycleSizeRepository extends JpaRepository<BicycleSize, String> {
    Optional<BicycleSize> findByName(String name);
}
