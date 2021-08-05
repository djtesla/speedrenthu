package speedrenthu.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import speedrenthu.machine.Machine;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRevenueBySegmentCommand {

    @NotBlank
    @Schema(description = "name of the segment", example = "CLEANING")
    private Machine.Segment segment;
}
