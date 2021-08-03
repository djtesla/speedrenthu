package speedrenthu.order;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import speedrenthu.EntityNotFoundException;
import speedrenthu.machine.Machine;
import speedrenthu.machine.MachineDto;
import speedrenthu.machine.MachineRepository;
import speedrenthu.machine.MachineService;
import speedrenthu.pricecategory.PriceCategory;
import speedrenthu.pricecategory.PriceCategoryDto;
import speedrenthu.pricecategory.PriceCategoryService;

import javax.transaction.Transactional;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private ModelMapper modelMapper;
    private MachineService machineService;
    private PriceCategoryService priceCategoryService;
    private OrderRepository orderRepository;

    @Transactional
    public OrderDto createOrder(CreateOrderCommand command) {
        MachineDto machineDto = machineService.findMachineByName(command.getName());
        Machine machine = modelMapper.map(machineDto, Machine.class);
        PriceCategoryDto priceCategoryDto = priceCategoryService.findPriceCategoryByMachineAndDuration(machine, command.getDuration());
        PriceCategory priceCategory = modelMapper.map(priceCategoryDto, PriceCategory.class);
        Order order = new Order(command.getDate(), command.getLocation(), Order.Status.WIP);
        priceCategory.addOrder(order);
        orderRepository.save(order);
        return  modelMapper.map(order, OrderDto.class);
    }

    public List<OrderDto> findOrdersByMachineId(long id) {
        machineService.getMachineById(id);
        List<Order> orders = orderRepository.findOrdersByMachineId(id);
        return orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
    }

    @Transactional
    public OrderDto updateOrderByStatus(long id, UpdateStatusCommand command) {
        Order order = orderRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Order cannot be found by id " + id));
        order.setStatus(command.getStatus());
        return modelMapper.map(order, OrderDto.class);
    }

    @Transactional
    public void deleteOrdersByDateBefore(DeleteOrdersCommand command) {
        orderRepository.deleteOrdersByDateBefore(command.getDate());
    }

    @Transactional
    public void deleteOrderById(long id) {
        Order order = orderRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Order cannot be found by id " + id));
        orderRepository.deleteById(id);
    }

    public OrderDto findOrderById(long id) {
        Order order = orderRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Order cannot be found by id " + id));
        return modelMapper.map(order, OrderDto.class);
    }
}
