package speedrenthu.machine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CreateMachineCommand {

    @Length(min =1, max = 50)
    private String name;
    @NotNull
    private Machine.Segment segment;
}
