package speedrenthu;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/speedrenthu/machines")
public class MachineController {

    private MachineService machineService;

    @GetMapping
    public List<MachineDto> listAllMachines() {
        return machineService.listAllMachines();
    }

    @GetMapping("/{id}")
    public MachineDto getMachineById(@PathVariable ("id") long id) {
        return machineService.getMachineById(id);
    }

    @PostMapping
    public MachineDto createMachine(@RequestBody @Valid CreateMachineCommand command) {
        return machineService.createMachine(command);
    }

    @PutMapping
    public List<MachineDto> updateSegmentName(@RequestParam String segment, @RequestBody UpdateSegmentCommand command) {
        return machineService.updateSegmentName(segment, command);
    }

    @DeleteMapping("/{id}")
    public void deleteMachineById(@PathVariable ("id") long id) {
        machineService.deleteMachineById(id);
    }

    @ExceptionHandler(MachineNotFoundException.class)
    public ResponseEntity<Problem> handleMovieNotFound(MachineNotFoundException mnfe) {
        Problem problem = Problem.builder()
                .withType(URI.create("machine/not-found"))
                .withStatus(Status.NOT_FOUND)
                .withTitle("Not found")
                .withDetail(mnfe.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problem);
    }

}
