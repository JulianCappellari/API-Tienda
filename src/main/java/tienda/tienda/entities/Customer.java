package tienda.tienda.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity(name = "Customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String address;
    private Integer number;


    // Cada cliente puede tener muchos pedidos (One-to-Many).
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
