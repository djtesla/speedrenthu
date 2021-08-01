package speedrenthu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMachineCommand {

    @Length(min =1, max = 50)
    private String name;
    private Machine.Segment segment;
}
