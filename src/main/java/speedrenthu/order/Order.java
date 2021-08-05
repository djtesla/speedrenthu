package speedrenthu.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import speedrenthu.pricecategory.PriceCategory;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    public enum Status {
        WIP, COMPLETED, CANCELED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String location;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "price_category_id")
    private PriceCategory priceCategory;

    public Order(LocalDate date, String location, Status status) {
        this.date = date;
        this.location = location;
        this.status = status;
    }
}
