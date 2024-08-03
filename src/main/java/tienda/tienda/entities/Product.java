package tienda.tienda.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity(name = "Product")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private int stock;
    private String urlImage;

    // Cada producto pertenece a una categoría (Many-to-One).
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
