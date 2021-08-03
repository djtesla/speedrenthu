package speedrenthu.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import speedrenthu.pricecategory.PriceCategory;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private long id;
    private LocalDate date;
    private String location;
    private Order.Status status;
    private PriceCategory priceCategory;
}
