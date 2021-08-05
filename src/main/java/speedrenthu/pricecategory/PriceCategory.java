package speedrenthu.pricecategory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import speedrenthu.machine.Machine;
import speedrenthu.order.Order;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "price_categories")
public class PriceCategory {

    public enum Duration {
        THREE_HOURS, ONE_DAY, WEEKEND
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true,  mappedBy = "priceCategory")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private List<Order> orders;

    @Enumerated(EnumType.STRING)
    private Duration duration;

    private long amount;


    public PriceCategory(Machine machine, Duration duration, long amount) {
        this.machine = machine;
        this.duration = duration;
        this.amount = amount;
    }

    public PriceCategory(Duration duration, long amount) {
        this.duration = duration;
        this.amount = amount;
    }

    public void addOrder(Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        order.setPriceCategory(this);
        orders.add(order);
    }
}
