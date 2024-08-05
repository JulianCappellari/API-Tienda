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
import tienda.tienda.dto.CategoryDto;
import tienda.tienda.entities.Category;
import tienda.tienda.services.category.CategoryService;
import tienda.tienda.transformers.CategoryMapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "Operations related to categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Operation(summary = "Get all categorys")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Category found successfully",
            content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class))
            }),
            @ApiResponse(responseCode = "500", description = "Category not found", content = @Content)
    })
    @GetMapping
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryService.getAll();
        return categories.stream().map(categoryMapper::toDTO).toList();
    }

    @Operation(summary = "Get category by id")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Category found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Category.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Category not found with id", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@Parameter(description = "Category id", example = "1")@PathVariable Long id ) {
        Optional<Category> category = categoryService.getById(id);
        return category.map( c -> ResponseEntity.ok(categoryMapper.toDTO(c))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create category in database")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Category created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Category.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content),
            @ApiResponse(responseCode = "400", description = "Response error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        Category category = categoryMapper.toEntity(categoryDto);
        Category categorySaved = categoryService.add(category);
        return  ResponseEntity.ok(categoryMapper.toDTO(categorySaved));

    }


    @Operation(summary = "Update category in database")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Category updated successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Category.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content),
            @ApiResponse(responseCode = "400", description = "Response error", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Category categoryToUpdate = categoryMapper.toEntity(categoryDto);
        Category updatedCategory = categoryService.update(categoryToUpdate, id);
        return updatedCategory != null ? ResponseEntity.ok(categoryMapper.toDTO(updatedCategory)) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete category in database by id")
    @ApiResponses( value = {
            @ApiResponse( responseCode = "200", description = "Category deleted successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Category.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Parameter error", content = @Content)

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@Parameter(description = "Category id", example = "1") @PathVariable Long id){
        if(categoryService.getById(id).isPresent()){
            categoryService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
