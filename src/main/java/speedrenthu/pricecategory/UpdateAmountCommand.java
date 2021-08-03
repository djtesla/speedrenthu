package speedrenthu.pricecategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAmountCommand {

    @Min(3000)
    @Max(20000)
    private int amount;
}
