package tienda.tienda.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

@Entity(name = "OrdenDetail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Double unitPrice;

    // Cada detalle de pedido pertenece a un pedido (Many-to-One).
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Cada detalle de pedido está asociado a un producto (Many-to-One).
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
