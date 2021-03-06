package speedrenthu.order;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import speedrenthu.EntityNotFoundException;
import speedrenthu.Violation;
import speedrenthu.machine.CreateMachineCommand;
import speedrenthu.machine.MachineDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/speedrenthu/orders")
public class OrderController {

    private OrderService orderService;

    @GetMapping("/{id}")
    @Operation(summary = "find order by id")
    public OrderDto findOrderById(@PathVariable("id") long id) {
        return orderService.findOrderById(id);
    }


    @GetMapping()
    @Operation(summary = "list all orders")
    public List<OrderDto> listAllOrders() {
        return orderService.listAllOrders();
    }


    @GetMapping("/machine/{id}")
    @Operation(summary = "find orders by machine id")
    public List<OrderDto> findOrdersByMachineId(@PathVariable("id") long id) {
        return orderService.findOrdersByMachineId(id);
    }

    @GetMapping("/revenue_by_segment")
    @Operation(summary = "get revenue by segment")
    public List<RevenueBySegmentDto> getRevenueBySegment() {
        return orderService.getRevenueBySegment();
    }


    @GetMapping("/machine/{id}/revenue")
    @Operation(summary = "get revenue by machine id")
    public List<RevenueByMachineDto> getRevenueByMachine(@PathVariable("id") long machineId) {
        return orderService.getRevenueByMachine(machineId);
    }


    @PostMapping
    @Operation(summary = "create an order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestBody @Valid CreateOrderCommand command) {
        return orderService.createOrder(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update order status")
    public OrderDto updateOrderByStatus(@PathVariable("id") long id, @RequestBody @Valid UpdateStatusCommand command) {
        return orderService.updateOrderByStatus(id, command);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete order by id")
    public void deleteOrderById(@PathVariable("id") long id) {
        orderService.deleteOrderById(id);
    }

    @DeleteMapping
    @Operation(summary = "delete orders before certain date")
    public void deleteOrdersByDateBefore(@RequestBody @Valid DeleteOrdersCommand command) {
        orderService.deleteOrdersByDateBefore(command);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Problem> handleOrderNotFound(EntityNotFoundException enfe) {
        Problem problem = Problem.builder()
                .withType(URI.create("order/not-found"))
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
                .withType(URI.create("order/not-valid"))
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
