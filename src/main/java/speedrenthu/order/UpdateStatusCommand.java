package speedrenthu.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusCommand {

    @NotNull
    @Schema(description = "new status of order", example = "COMPLETED")
    private Order.Status status;
}
