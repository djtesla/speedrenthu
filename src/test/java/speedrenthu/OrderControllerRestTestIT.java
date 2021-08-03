package speedrenthu;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import speedrenthu.machine.CreateMachineCommand;
import speedrenthu.machine.Machine;
import speedrenthu.machine.MachineDto;
import speedrenthu.machine.UpdateSegmentCommand;
import speedrenthu.order.CreateOrderCommand;
import speedrenthu.order.Order;
import speedrenthu.order.OrderDto;
import speedrenthu.order.UpdateStatusCommand;
import speedrenthu.pricecategory.PriceCategory;
import speedrenthu.pricecategory.PriceCategoryDto;
import speedrenthu.pricecategory.UpdateAmountCommand;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerRestTestIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    Flyway flyway;

    @BeforeEach
    public void init() {
        flyway.clean();
        flyway.migrate();
    }


    @Test
    void testCreate() {
        OrderDto order = template.postForObject("/api/speedrenthu/orders", new CreateOrderCommand(
                "steam cleaner",
                PriceCategory.Duration.THREE_HOURS,
                LocalDate.of(2021, 8, 10),
                "Budapest"), OrderDto.class);
        assertAll(
                () -> assertEquals("steam cleaner", order.getPriceCategory().getMachine().getName()),
                () -> assertEquals("Budapest", order.getLocation())
        );
    }

    @Test
    void testList() {
        List<OrderDto> result = template.exchange(
                "/api/speedrenthu/orders/by_machine/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderDto>>() {
                }).getBody();
        assertEquals(3, result.size());
    }


    @Test
    void testUpdate() {
        template.put("/api/speedrenthu/orders/1", new UpdateStatusCommand(Order.Status.COMPLETED));
        OrderDto order = template.getForObject("/api/speedrenthu/orders/1",OrderDto.class);
        assertEquals(Order.Status.COMPLETED, order.getStatus());
    }

    @Test
    void testDelete() {
        template.delete("/api/speedrenthu/orders/1");
        Problem result = template.getForObject("/api/speedrenthu/orders/1", Problem.class);
        assertEquals(URI.create("order/not-found"),result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }


}









