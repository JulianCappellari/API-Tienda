package tienda.tienda.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tienda.tienda.dto.ProductDto;
import tienda.tienda.entities.Product;
import tienda.tienda.services.product.ProductService;
import tienda.tienda.transformers.ProductMapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        List<Product> products = productService.getAll();
        return products.stream().map(productMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        Optional<Product> product = productService.getById(id);
        return product.map( p -> ResponseEntity.ok(productMapper.toDto(p))).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        if (productDto.getCategoryId() == null) {
            return ResponseEntity.badRequest().body(null); // O alguna respuesta adecuada
        }
        Product product = productMapper.toEntity(productDto);
        Product productSaved = productService.add(product);
        return ResponseEntity.ok(productMapper.toDto(productSaved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product productToUpdate = productMapper.toEntity(productDto);
        Product updatedProduct = productService.update(productToUpdate, id);
        return updatedProduct != null ? ResponseEntity.ok(productMapper.toDto(updatedProduct)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
        if(productService.getById(id).isPresent()){
            productService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
