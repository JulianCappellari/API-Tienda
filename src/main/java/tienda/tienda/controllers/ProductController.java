package tienda.tienda.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tienda.tienda.dto.ProductDto;
import tienda.tienda.entities.Category;
import tienda.tienda.entities.Product;
import tienda.tienda.services.product.ProductService;
import tienda.tienda.transformers.ProductMapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "Operations related to products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;


    @Operation(summary = "Get all products")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Products found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Products not found", content = @Content)
    })
    @GetMapping
    public List<ProductDto> getAllProducts() {
        List<Product> products = productService.getAll();
        return products.stream().map(productMapper::toDto).toList();
    }


    @Operation(summary = "Get product by id")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Product found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Product not found with id", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@Parameter(description = "Product id", example = "1")@PathVariable Long id){
        Optional<Product> product = productService.getById(id);
        return product.map( p -> ResponseEntity.ok(productMapper.toDto(p))).orElseGet( () -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Create product in database")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Product created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content),
            @ApiResponse(responseCode = "400", description = "Response error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        if (productDto.getCategoryId() == null) {
            return ResponseEntity.badRequest().body(null); // O alguna respuesta adecuada
        }
        Product product = productMapper.toEntity(productDto);
        Product productSaved = productService.add(product);
        return ResponseEntity.ok(productMapper.toDto(productSaved));
    }


    @Operation(summary = "Update product in database")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Product updated successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content),
            @ApiResponse(responseCode = "400", description = "Response error", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product productToUpdate = productMapper.toEntity(productDto);
        Product updatedProduct = productService.update(productToUpdate, id);
        return updatedProduct != null ? ResponseEntity.ok(productMapper.toDto(updatedProduct)) : ResponseEntity.notFound().build();
    }


    @Operation(summary = "Delete product in database by id")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Product deleted successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content)

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@Parameter(description = "Product id", example = "1")@PathVariable Long id) {
        if(productService.getById(id).isPresent()){
            productService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
