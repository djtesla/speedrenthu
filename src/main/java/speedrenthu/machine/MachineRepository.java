package speedrenthu.machine;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    List<Machine> findMachinesBySegment(Machine.Segment segment);

    Optional<Machine> findMachineByName(String name);



}
