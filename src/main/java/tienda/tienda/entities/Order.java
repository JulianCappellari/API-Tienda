package tienda.tienda.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp date;
    private Double total;
    private String status;

    // Cada pedido pertenece a un cliente (Many-to-One).
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Cada pedido puede tener muchos detalles de pedido (One-to-Many).
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

}
