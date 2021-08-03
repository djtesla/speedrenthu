package speedrenthu.pricecategory;

import org.springframework.data.jpa.repository.JpaRepository;
import speedrenthu.machine.Machine;
import speedrenthu.machine.MachineDto;

import java.util.Optional;

public interface PriceCategoryRepository extends JpaRepository<PriceCategory, Long> {

    public Optional<PriceCategory> findPriceCategoryByMachineAndDuration(Machine machine, PriceCategory.Duration duration);


}
