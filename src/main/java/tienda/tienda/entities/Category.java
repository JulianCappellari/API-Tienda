package tienda.tienda.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity(name = "Category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String description;

    // Cada categoría puede tener muchos productos (One-to-Many).
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
