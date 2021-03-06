package speedrenthu.pricecategory;


import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/speedrenthu/price_categories")
public class PriceCategoryController {

    private PriceCategoryService priceCategoryService;

    @GetMapping
    @Operation(summary = "list all price categories")
    private List<PriceCategoryDto> listPriceCategories() {
        return priceCategoryService.listPriceCategories();
    }

    @GetMapping("/{id}")
    @Operation(summary = "get price category by id")
    private PriceCategoryDto findPriceCategoryById(@PathVariable("id") long id) {
        return priceCategoryService.findPriceCategoryById(id);
    }

    @PostMapping
    @Operation(summary = "create a price category")
    @ResponseStatus(HttpStatus.CREATED)
    public PriceCategoryDto createPriceCategory(@RequestBody @Valid CreatePriceCategoryCommand command) {
        return priceCategoryService.createPriceCategory(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update price category with amount")
    public PriceCategoryDto updatePriceCategoryWithAmount(@PathVariable("id") long id, @RequestBody @Valid UpdateAmountCommand command) {
        return priceCategoryService.updatePriceCategoryWithAmount(id, command);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete price category by id")
    public void deletePriceCategoryById(@PathVariable("id") long id) {
        priceCategoryService.deletePriceCategoryById(id);
    }

    @ExceptionHandler(PriceCategoryAlreadyExistsException.class)
    public ResponseEntity<Problem> handlePriceCategoryAlreadyExists(PriceCategoryAlreadyExistsException paee) {
        Problem problem = Problem.builder()
                .withType(URI.create("pricecategory/already-exists"))
                .withStatus(Status.NOT_FOUND)
                .withTitle("Already exists")
                .withDetail(paee.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(problem);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Problem> handlePriceCategoryNotFound(EntityNotFoundException enfe) {
        Problem problem = Problem.builder()
                .withType(URI.create("pricecategory/not-found"))
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
                .withType(URI.create("pricecategory/not-valid"))
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
