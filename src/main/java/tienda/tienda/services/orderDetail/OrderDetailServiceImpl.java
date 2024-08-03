package tienda.tienda.services.orderDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tienda.tienda.entities.OrderDetail;
import tienda.tienda.repositories.OrderDetailRepository;

import java.util.List;
import java.util.Optional;


@Service
public class OrderDetailServiceImpl implements OrderDetailService{
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository){
        this.orderDetailRepository = orderDetailRepository;
    }


    @Override
    public OrderDetail add(OrderDetail entity) {
        return orderDetailRepository.save(entity);
    }

    @Override
    public Optional<OrderDetail> getById(Long id) {
        return orderDetailRepository.findById(id);
    }

    @Override
    public List<OrderDetail> getAll() {
        return orderDetailRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public OrderDetail update(OrderDetail entity, Long id) {
        return orderDetailRepository.findById(id).map(orderDetail -> {
            orderDetail.setQuantity(entity.getQuantity());
            orderDetail.setUnitPrice(entity.getUnitPrice());
            orderDetail.setOrder(entity.getOrder());
            orderDetail.setProduct(entity.getProduct());
            return orderDetailRepository.save(orderDetail);
        }).orElse(null);
    }
}
