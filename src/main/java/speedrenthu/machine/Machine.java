package speedrenthu.machine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import speedrenthu.pricecategory.PriceCategory;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name = "machines")

public class Machine {

    public enum Segment {
        BUILDING, BUILDING_AND_REPAIRING, CLEANING, GARDENING
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Segment segment;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "machine")
    //@OrderBy("duration")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private List<PriceCategory> priceCategories;

    public Machine(String name, Segment segment) {
        this.name = name;
        this.segment = segment;
    }


    public void addPriceCategory(PriceCategory priceCategory) {
        if (priceCategories == null) {
            priceCategories = new ArrayList<>();
        }
        priceCategories.add(priceCategory);
        priceCategory.setMachine(this);
    }

    public String getName() {
        return name;
    }

    public Segment getSegment() {
        return segment;
    }
}
