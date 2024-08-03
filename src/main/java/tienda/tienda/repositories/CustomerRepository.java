package tienda.tienda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tienda.tienda.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
