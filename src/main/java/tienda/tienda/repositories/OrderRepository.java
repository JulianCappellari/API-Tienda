package tienda.tienda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tienda.tienda.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
