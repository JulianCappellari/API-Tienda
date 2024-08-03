package tienda.tienda.controllers;


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
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryService.getAll();
        return categories.stream().map(categoryMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getById(id);
        return category.map( c -> ResponseEntity.ok(categoryMapper.toDTO(c))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        Category category = categoryMapper.toEntity(categoryDto);
        Category categorySaved = categoryService.add(category);
        return  ResponseEntity.ok(categoryMapper.toDTO(categorySaved));

    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Category categoryToUpdate = categoryMapper.toEntity(categoryDto);
        Category updatedCategory = categoryService.update(categoryToUpdate, id);
        return updatedCategory != null ? ResponseEntity.ok(categoryMapper.toDTO(updatedCategory)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long id){
        if(categoryService.getById(id).isPresent()){
            categoryService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
