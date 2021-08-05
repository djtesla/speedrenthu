package speedrenthu.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import speedrenthu.pricecategory.PriceCategory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueByMachineDto {

    Long id;
    //private String name;
    private PriceCategory.Duration duration;
    private long amount;
}
