package speedrenthu.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join o.priceCategory p where p.machine.id =:id")
    public List<Order> findOrdersByMachineId(long id);

    public void deleteOrdersByDateBefore(LocalDate date);


}
