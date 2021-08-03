package speedrenthu.machine;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import speedrenthu.EntityNotFoundException;
import speedrenthu.Violation;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
    @ResponseStatus(HttpStatus.CREATED)
    public MachineDto createMachine(@RequestBody @Valid CreateMachineCommand command) {
        return machineService.createMachine(command);
    }

    @PutMapping
    public List<MachineDto> updateSegmentName(@RequestParam String segment, @RequestBody @Valid UpdateSegmentCommand command) {
        return machineService.updateSegmentName(segment, command);
    }

    @DeleteMapping("/{id}")
    public void deleteMachineById(@PathVariable ("id") long id) {
        machineService.deleteMachineById(id);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Problem> handleMovieNotFound(EntityNotFoundException enfe) {
        Problem problem = Problem.builder()
                .withType(URI.create("machine/not-found"))
                .withStatus(Status.NOT_FOUND)
                .withTitle("Not found")
                .withDetail(enfe.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> handleNotValidException(MethodArgumentNotValidException mae) {

        List<Violation> violations = mae.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new Violation(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        Problem problem = Problem.builder()
                .withType(URI.create("entity/not-valid"))
                .withTitle(("Validation error"))
                .withStatus(Status.BAD_REQUEST)
                .withDetail(mae.getMessage())
                .with("violations", violations)
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problem);
    }

}
