package tienda.tienda.transformers;

import org.springframework.stereotype.Component;
import tienda.tienda.dto.OrderDto;
import tienda.tienda.entities.Customer;
import tienda.tienda.entities.Order;


@Component
public class OrderMapper {
    public OrderDto toDto(Order order){
        return new OrderDto(
                order.getId(),
                order.getCustomer().getId(), // Obtener el ID del cliente
                order.getDate(),
                order.getTotal(),
                order.getStatus()
        );
    }
    public Order toEntity(OrderDto orderDto) {
        Customer customer = new Customer(); // Crear un objeto Customer con solo el ID
        customer.setId(orderDto.getCustomerId());
        return new Order(
                orderDto.getId(),
                orderDto.getDate(),
                orderDto.getTotal(),
                orderDto.getStatus(),
                customer, // Establecer la categoría correcta
                null // Inicializar la lista de detalles de pedido como null
        );
    }
}
