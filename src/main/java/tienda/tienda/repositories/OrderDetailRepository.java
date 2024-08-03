package tienda.tienda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tienda.tienda.entities.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
