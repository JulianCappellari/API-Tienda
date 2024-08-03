package tienda.tienda.transformers;

import org.springframework.stereotype.Component;
import tienda.tienda.dto.ProductDto;
import tienda.tienda.entities.Category;
import tienda.tienda.entities.Product;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product){
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getUrlImage());
    }

    public Product toEntity(ProductDto productDto) {
        Category category = new Category();
        if (productDto.getCategoryId() != null) {
            category.setId(productDto.getCategoryId());
        } else {
            // Manejar el caso en el que categoryId es nulo
            throw new IllegalArgumentException("Category ID must not be null");
        }
        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getStock(),
                productDto.getUrlImage(),
                category
        );
    }
}
