package speedrenthu.machine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineDto {

    private Long id;
    private String name;
    private Machine.Segment segment;

}
