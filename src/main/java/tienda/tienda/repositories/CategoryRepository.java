package tienda.tienda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tienda.tienda.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
