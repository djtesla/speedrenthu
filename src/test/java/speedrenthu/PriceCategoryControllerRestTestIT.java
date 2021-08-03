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
import speedrenthu.pricecategory.CreatePriceCategoryCommand;
import speedrenthu.pricecategory.PriceCategory;
import speedrenthu.pricecategory.PriceCategoryDto;
import speedrenthu.pricecategory.UpdateAmountCommand;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceCategoryControllerRestTestIT {


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
        template.postForObject("/api/speedrenthu/machines", new CreateMachineCommand("rock drill", Machine.Segment.BUILDING), MachineDto.class);
        PriceCategoryDto priceCategory = template.postForObject("/api/speedrenthu/price_categories", new CreatePriceCategoryCommand("rock drill", PriceCategory.Duration.ONE_DAY, 10000), PriceCategoryDto.class);
        assertAll(
                () -> assertEquals("rock drill", priceCategory.getMachine().getName()),
                () -> assertEquals(PriceCategory.Duration.ONE_DAY, priceCategory.getDuration()),
                () -> assertEquals(10000, priceCategory.getAmount())
        );
    }

    @Test
    void testList() {
        List<PriceCategoryDto> result = template.exchange(
                "/api/speedrenthu/price_categories",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PriceCategoryDto>>() {
                }).getBody();
        assertEquals(9, result.size());
    }

    @Test
    void testUpdate() {
        template.put("/api/speedrenthu/price_categories/1", new UpdateAmountCommand(8500));
        PriceCategoryDto priceCategoryDto = template.getForObject("/api/speedrenthu/price_categories/1",PriceCategoryDto.class);
        assertEquals(8500, priceCategoryDto.getAmount());

    }

    @Test
    void testDelete() {
        template.delete("/api/speedrenthu/price_categories/1");
        Problem result = template.getForObject("/api/speedrenthu/price_categories/1", Problem.class);
        assertEquals(URI.create("pricecategory/not-found"),result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());
    }


}
