package speedrenthu.machine;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSegmentCommand {

    @Schema(description = "new name for segment", example = "BUILDING_AND_REPAIRING")
    @NotNull
    private Machine.Segment segment;
}
