package speedrenthu.pricecategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import speedrenthu.machine.MachineDto;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceCategoryDto {

    private  Long id;
    private MachineDto machine;
    private PriceCategory.Duration duration;
    private int amount;

    public PriceCategoryDto(MachineDto machine, PriceCategory.Duration duration, int amount) {
        this.machine = machine;
        this.duration = duration;
        this.amount = amount;
    }
}
