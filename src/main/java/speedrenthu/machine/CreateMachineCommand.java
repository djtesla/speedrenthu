package speedrenthu.machine;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateMachineCommand {

    @Length(min =1, max = 50)
    @Schema(description = "name of machine", example = "rock drill")
    private String name;
    @NotNull

    @Schema(description = "segment of machine", example = "BUILDING")
    private Machine.Segment segment;
}
