package tienda.tienda.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Date;

@Entity(name = "Payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private Timestamp paymentDate;
    private String paymentMethod;

    // Cada pago pertenece a un pedido (Many-to-One).
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
