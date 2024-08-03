package tienda.tienda.transformers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tienda.tienda.dto.OrderDetailDto;
import tienda.tienda.entities.Order;
import tienda.tienda.entities.OrderDetail;
import tienda.tienda.entities.Product;
import tienda.tienda.services.order.OrderService;
import tienda.tienda.services.product.ProductService;


@Component
public class OrderDetailMapper {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    public OrderDetailDto toDto(OrderDetail orderDetail) {
        Long productId = null;
        if (orderDetail.getProduct() != null) {
            productId = orderDetail.getProduct().getId();
        }

        return new OrderDetailDto(
                orderDetail.getId(),
                orderDetail.getOrder().getId(),
                productId,
                orderDetail.getQuantity(),
                orderDetail.getUnitPrice()
        );
    }

    public OrderDetail toEntity(OrderDetailDto orderDetailDto){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(orderDetailDto.getId());
        orderDetail.setQuantity(orderDetailDto.getQuantity());
        orderDetail.setUnitPrice(orderDetailDto.getUnitPrice());
        Order order = orderService.getById(orderDetailDto.getOrderId()).orElse(null);
        Product product = productService.getById(orderDetailDto.getProductId()).orElse(null);

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);

        return orderDetail;
    }
}
