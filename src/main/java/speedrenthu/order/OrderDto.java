package speedrenthu.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import speedrenthu.pricecategory.PriceCategory;
import speedrenthu.pricecategory.PriceCategoryDto;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private long id;
    private LocalDate date;
    private String location;
    private Order.Status status;
    private PriceCategoryDto priceCategory;
}
