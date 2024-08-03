package tienda.tienda.services.product;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tienda.tienda.entities.Category;
import tienda.tienda.entities.Product;
import tienda.tienda.repositories.CategoryRepository;
import tienda.tienda.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Product add(Product product) {
        Category category = product.getCategory();
        if (category != null && category.getId() != null) {
            Optional<Category> existingCategory = categoryRepository.findById(category.getId());
            if (existingCategory.isPresent()) {
                product.setCategory(existingCategory.get());
            } else {
                // Manejar el caso en el que la categoría no exista
                throw new IllegalArgumentException("Category with ID " + category.getId() + " does not exist");
            }
        } else {
            // Manejar el caso en el que la categoría es nula o su ID es nulo
            throw new IllegalArgumentException("Category or Category ID must not be null");
        }
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product update(Product entity, Long id) {
        return productRepository.findById(id).map( productFound -> {
            productFound.setName(entity.getName());
            productFound.setDescription(entity.getDescription());
            productFound.setPrice(entity.getPrice());
            productFound.setStock(entity.getStock());
            productFound.setCategory(entity.getCategory());
            productFound.setUrlImage(entity.getUrlImage());
            return productRepository.save(productFound);
        }).orElse(null);
    }
}
