package speedrenthu.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import speedrenthu.machine.Machine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join o.priceCategory p where p.machine.id =:id")
    List<Order> findOrdersByMachineId(long id);

    void deleteOrdersByDateBefore(LocalDate date);


    @Query("select p.machine.segment, sum(p.amount) from Order o join o.priceCategory p group by p.machine.segment")
    List<Object[]> getRevenueBySegment();


    @Query("select p.machine.name, sum(p.amount) from Order o join o.priceCategory p group by p.machine.name having p.machine.id =:machineId")
    List<Object[]> getRevenueByMachine(long machineId);
}
