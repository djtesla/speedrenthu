package speedrenthu;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MachineService {

    private MachineRepository machineRepository;

    private ModelMapper modelMapper;

    public MachineDto createMachine(CreateMachineCommand command) {
        Machine machine = new Machine(command.getName(), command.getSegment());
        machineRepository.save(machine);
        return modelMapper.map(machine, MachineDto.class);
    }

    public List<MachineDto> listAllMachines() {
        List<Machine> machines =  machineRepository.findAll();
        return machines.stream().map(machine -> modelMapper.map(machine, MachineDto.class)).collect(Collectors.toList());
    }

    public MachineDto getMachineById(long id) {
        Machine machine = machineRepository.findById(id).orElseThrow(()-> new MachineNotFoundException("Machine cannot be found by id " + id));
        return modelMapper.map(machine, MachineDto.class);
    }

    @Transactional
    public List<MachineDto> updateSegmentName(String segmentStr, UpdateSegmentCommand command) {
        Machine.Segment segmentToBeFound = Machine.Segment.valueOf(segmentStr.toUpperCase());
        System.out.println(segmentToBeFound);
        Arrays.stream(Machine.Segment.values()).filter(segment -> segment==segmentToBeFound).findAny().orElseThrow(()-> new IllegalArgumentException("Segment cannot be found: " + segmentStr));
        List<Machine> machines = machineRepository.findMachinesBySegment(segmentToBeFound);
        machines.stream().forEach(machine -> machine.setSegment(command.getSegment()));
        return machines.stream().map(machine -> modelMapper.map(machine, MachineDto.class)).collect(Collectors.toList());
    }

    public void deleteMachineById(long id) {
        Machine machine = machineRepository.findById(id).orElseThrow(()-> new MachineNotFoundException("Machine cannot be found by id " + id));
        machineRepository.delete(machine);
    }
}
