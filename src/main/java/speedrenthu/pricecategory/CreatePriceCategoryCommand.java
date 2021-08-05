package speedrenthu.pricecategory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class CreatePriceCategoryCommand {

    @NotBlank
    @Schema(description = "name of the machine", example = "rock drill")
    String name;


    @NotBlank
    @Schema(description = "duration of rent", example = "THREE_HOURS")
    PriceCategory.Duration duration;

    @Min(3000)
    @Max(20000)
    @Schema(description = "price of rent", example = "4000")
    int amount;
}
