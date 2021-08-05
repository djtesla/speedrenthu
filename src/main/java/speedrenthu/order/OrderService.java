package speedrenthu.order;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import speedrenthu.EntityNotFoundException;
import speedrenthu.machine.Machine;
import speedrenthu.machine.MachineDto;
import speedrenthu.machine.MachineService;
import speedrenthu.pricecategory.PriceCategory;
import speedrenthu.pricecategory.PriceCategoryDto;
import speedrenthu.pricecategory.PriceCategoryService;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
        return modelMapper.map(order, OrderDto.class);
    }

    public List<OrderDto> listAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
    }


    public   List<RevenueBySegmentDto> getRevenueBySegment() {
        List<Object[]> objects  = orderRepository.getRevenueBySegment();
        List<RevenueBySegmentDto> revenues = new ArrayList<>();
        for (Object[] element : objects){
            revenues.add(new RevenueBySegmentDto((Machine.Segment) element[0], (long)element[1]));
        }
        return revenues;
    }

    public List<RevenueByMachineDto> getRevenueByMachine(long machineId) {
        List<Object[]> objects  = orderRepository.getRevenueByMachine(machineId);
        List<RevenueByMachineDto> revenues = new ArrayList<>();
        for (Object[] element : objects){
            revenues.add(new RevenueByMachineDto((long)element[0],(PriceCategory.Duration) element[1], (long)element[2]));
            //revenues.add(new RevenueByMachineDto((long)element[0], (String) element[1], (PriceCategory.Duration) element[2], (long)element[3]));
            //revenues.add(new RevenueByMachineDto((PriceCategory.Duration) element[0], (long)element[1]));
        }
        return revenues;

    }


    public List<OrderDto> findOrdersByMachineId(long id) {
        machineService.getMachineById(id);
        List<Order> orders = orderRepository.findOrdersByMachineId(id);
        return orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
    }


    @Transactional
    public OrderDto updateOrderByStatus(long id, UpdateStatusCommand command) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order cannot be found by id " + id));
        order.setStatus(command.getStatus());
        return modelMapper.map(order, OrderDto.class);
    }

    @Transactional
    public void deleteOrdersByDateBefore(DeleteOrdersCommand command) {
        orderRepository.deleteOrdersByDateBefore(command.getDate());
    }

    @Transactional
    public void deleteOrderById(long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order cannot be found by id " + id));
        orderRepository.deleteById(id);
    }


    public OrderDto findOrderById(long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order cannot be found by id " + id));
        return modelMapper.map(order, OrderDto.class);
    }


}


