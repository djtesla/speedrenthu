package speedrenthu.pricecategory;

import org.springframework.data.jpa.repository.JpaRepository;
import speedrenthu.machine.Machine;

import java.util.Optional;

public interface PriceCategoryRepository extends JpaRepository<PriceCategory, Long> {

    Optional<PriceCategory> findPriceCategoryByMachineAndDuration(Machine machine, PriceCategory.Duration duration);


}
