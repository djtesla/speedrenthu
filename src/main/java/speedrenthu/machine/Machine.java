package speedrenthu.machine;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Data
@NoArgsConstructor
@Entity
@Table(name = "machines")
public class Machine {

    public enum Segment {
        BUILDING, BUILDING_AND_REPAIRING, CLEANING, GARDENING
    };

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String name;

   @Enumerated(EnumType.STRING)
    private Segment segment;

    public Machine(String name, Segment segment) {
        this.name = name;
        this.segment = segment;
    }

    public String getName() {
        return name;
    }

    public Segment getSegment() {
        return segment;
    }
}
