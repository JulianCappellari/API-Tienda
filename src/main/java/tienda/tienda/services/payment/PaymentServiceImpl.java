package tienda.tienda.services.payment;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tienda.tienda.entities.Order;
import tienda.tienda.entities.Payment;
import tienda.tienda.repositories.OrderRepository;
import tienda.tienda.repositories.PaymentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    public PaymentServiceImpl( PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }
    @Override
    @Transactional
    public Payment add(Payment payment) {
        Order order = payment.getOrder();
        if (order != null && order.getId() != null) {
            if (!orderRepository.existsById(order.getId())) {
                throw new EntityNotFoundException("Order not found with ID: " + order.getId());
            }
            // Guardar el objeto Order antes de guardar el pago
            orderRepository.save(order);
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> getById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public Payment update(Payment entity, Long id) {
        return paymentRepository.findById(id).map( payment -> {
            payment.setAmount(entity.getAmount());
            payment.setPaymentMethod(entity.getPaymentMethod());
            payment.setPaymentDate(entity.getPaymentDate());
            payment.setOrder(entity.getOrder());
            return paymentRepository.save(payment);
        }).orElse(null);
    }
}
