package tienda.tienda.transformers;

import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import tienda.tienda.dto.PaymentDto;
import tienda.tienda.entities.Order;
import tienda.tienda.entities.Payment;
import tienda.tienda.repositories.OrderRepository;


@Component
public class PaymentMapper {
    @Autowired
    private OrderRepository orderRepository;
    public PaymentDto toDto(Payment payment){
        return  new PaymentDto(
                payment.getId(),
                payment.getOrder().getId(), // Obtener el ID del pedido
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentMethod()
        );
    }
    public Payment toEntity(PaymentDto paymentDto) {
        if (paymentDto.getOrderId() != null) {
            Order order = orderRepository.findById(paymentDto.getOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + paymentDto.getOrderId()));
            return new Payment(
                    paymentDto.getId(),
                    paymentDto.getAmount(),
                    paymentDto.getPaymentDate(),
                    paymentDto.getPaymentMethod(),
                    order
            );
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order ID is required");
        }
    }
}
