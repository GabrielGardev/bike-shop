package bikeshop.repository;

import bikeshop.domain.entities.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComponentRepository extends JpaRepository<Component, String> {

    Optional<Component> findByTypeAndDescription(String type, String description);
}
