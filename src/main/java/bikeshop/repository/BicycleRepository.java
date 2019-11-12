package bikeshop.repository;

import bikeshop.domain.entities.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BicycleRepository extends JpaRepository<Bicycle, String> {
    List<Bicycle> findAllByCategoryName(String name);

    Optional<Bicycle> findByMakeAndModelAndColor(String make, String model, String color);

    @Transactional
    @Modifying
    @Query("update Bicycle set discount = 0")
    void setAllDiscountsToZero();

    List<Bicycle> findAllByDiscountIsGreaterThan(Double discountRate);
}
