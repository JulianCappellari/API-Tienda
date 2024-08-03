package tienda.tienda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tienda.tienda.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
