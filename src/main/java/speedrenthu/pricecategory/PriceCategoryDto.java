package speedrenthu.pricecategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import speedrenthu.machine.Machine;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceCategoryDto {

    private  Long id;
    private Machine machine;
    private PriceCategory.Duration duration;
    private int amount;

    public PriceCategoryDto(Machine machine, PriceCategory.Duration duration, int amount) {
        this.machine = machine;
        this.duration = duration;
        this.amount = amount;
    }
}
