package speedrenthu.order;

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
    private String name;
    private PriceCategory.Duration duration;
    @NotNull
    private LocalDate date;
    @NotBlank
    private String location;
}
