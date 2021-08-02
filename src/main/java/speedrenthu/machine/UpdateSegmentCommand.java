package speedrenthu.machine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSegmentCommand {

    private Machine.Segment segment;
}
