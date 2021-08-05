package speedrenthu.pricecategory;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "new price for price category", example = "6000")
    private int amount;
}
