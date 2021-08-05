package speedrenthu.pricecategory;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class CreatePriceCategoryCommand {

    @NotBlank
    String name;

    @NotNull
    PriceCategory.Duration duration;

    @Min(3000)
    @Max(20000)
    int amount;
}
