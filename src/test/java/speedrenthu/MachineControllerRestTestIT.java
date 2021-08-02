package speedrenthu;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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

import java.net.URI;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MachineControllerRestTestIT {


    @Autowired
    TestRestTemplate template;

    @Autowired
    Flyway flyway;

    @BeforeEach
    public void init(){
        flyway.clean();
        flyway.migrate();
    }


    @Test
    void testCreateMachine() {
        MachineDto machine = template.postForObject("/api/speedrenthu/machines", new CreateMachineCommand("rock drill", Machine.Segment.BUILDING), MachineDto.class);
        assertAll(
                () -> assertEquals("rock drill", machine.getName()),
                () -> assertEquals(Machine.Segment.BUILDING, machine.getSegment())
        );
    }

    @Test
    void testGetMachineById() {
        template.postForObject("/api/speedrenthu/machines", new CreateMachineCommand("drill", Machine.Segment.BUILDING), MachineDto.class);
        MachineDto machine = template.getForObject("/api/speedrenthu/machines/9", MachineDto.class);
        assertEquals("drill", machine.getName());
    }


    @Test
    void updateSegmentName() {
        template.put("/api/speedrenthu/machines?segment=building", new UpdateSegmentCommand(Machine.Segment.BUILDING_AND_REPAIRING));
        List<MachineDto> result = template.exchange(
                "/api/speedrenthu/machines",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MachineDto>>() {
                }).getBody();
        assertThat(result).filteredOn(machineDto -> machineDto.getSegment() == Machine.Segment.BUILDING_AND_REPAIRING).size().isEqualTo(3);
    }


    @Test
    void testDeleteMachineById() {
        template.delete("/api/speedrenthu/machines/1");
        Problem result = template.getForObject("/api/speedrenthu/machines/1", Problem.class);
        assertEquals(URI.create("machine/not-found"),result.getType());
        assertEquals(Status.NOT_FOUND, result.getStatus());

    }


}
