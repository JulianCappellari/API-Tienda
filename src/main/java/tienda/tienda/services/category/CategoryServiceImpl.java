package tienda.tienda.services.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tienda.tienda.entities.Category;
import tienda.tienda.repositories.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category add(Category entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public Optional<Category> getById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category update(Category entity, Long id) {
        return categoryRepository.findById(id).map(categoryFound -> {
            categoryFound.setName(entity.getName());
            categoryFound.setDescription(entity.getDescription());
            return categoryRepository.save(categoryFound);
        }).orElse(null);
    }
}
