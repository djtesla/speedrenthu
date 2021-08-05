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
import speedrenthu.order.*;
import speedrenthu.pricecategory.PriceCategory;

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
                "/api/speedrenthu/orders/machine/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OrderDto>>() {
                }).getBody();
        assertEquals(3, result.size());
    }


    @Test
    void testRevenueBySegment() {
        template.postForObject("/api/speedrenthu/orders", new CreateOrderCommand(
                "carpet cleaner",
                PriceCategory.Duration.THREE_HOURS,
                LocalDate.of(2021, 8, 15),
                "Vecsés"), OrderDto.class);
        List<RevenueBySegmentDto> result = template.exchange(
                "/api/speedrenthu/orders/revenue_by_segment",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RevenueBySegmentDto>>() {
                }).getBody();
        assertEquals(37000, result.get(0).getAmount());
    }

    @Test
    void testRevenueByMachine() {
        template.postForObject("/api/speedrenthu/orders", new CreateOrderCommand(
                "carpet cleaner",
                PriceCategory.Duration.THREE_HOURS,
                LocalDate.of(2021, 8, 15),
                "Vecsés"), OrderDto.class);
        List<RevenueByMachineDto> result = template.exchange(
                "/api/speedrenthu/orders/machine/1/revenue",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RevenueByMachineDto>>() {
                }).getBody();
        System.out.println(result);
        assertEquals(22000, result.stream().map(dto-> dto.getAmount()).mapToInt(amount -> amount.intValue()).sum());
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









