package speedrenthu.machine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMachineCommand {

    @Length(min =1, max = 50)
    private String name;
    private Machine.Segment segment;
}
