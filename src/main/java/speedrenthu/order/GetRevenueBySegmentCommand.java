package speedrenthu.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import speedrenthu.machine.Machine;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRevenueBySegmentCommand {

    private Machine.Segment segment;
}
