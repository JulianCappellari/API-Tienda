package tienda.tienda.services.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tienda.tienda.entities.Order;
import tienda.tienda.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public Order add(Order entity) {
        return orderRepository.save(entity);
    }

    @Override
    public Optional<Order> getById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order update(Order entity, Long id) {
        return orderRepository.findById(id).map( order -> {
            order.setDate(entity.getDate());
            order.setTotal(entity.getTotal());
            order.setStatus(entity.getStatus());
            order.setCustomer(entity.getCustomer());
            return orderRepository.save(order);
        }).orElse(null);
    }
}
