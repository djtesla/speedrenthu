package speedrenthu.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import speedrenthu.pricecategory.PriceCategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CreateOrderCommand {

    @NotBlank
    @Schema(description = "name of the machine", example = "rock drill")
    private String name;
    @Schema(description = "duration of rent", example = "THREE_HOURS")
    private PriceCategory.Duration duration;
    @NotNull
    @Schema(description = "start date of rent", example = "2021-08-01")
    private LocalDate date;
    @NotBlank
    @Schema(description = "location of rent", example = "Budapest")
    private String location;
}
