package speedrenthu.machine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSegmentCommand {

    @NotNull
    private Machine.Segment segment;
}
