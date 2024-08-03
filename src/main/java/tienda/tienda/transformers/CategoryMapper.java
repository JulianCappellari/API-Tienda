package tienda.tienda.transformers;



import org.springframework.stereotype.Component;
import tienda.tienda.dto.CategoryDto;
import tienda.tienda.entities.Category;


@Component
public class CategoryMapper {

    public CategoryDto toDTO(Category category) {
        return new CategoryDto(category.getId(), category.getName(), category.getDescription());
    }

    public Category toEntity(CategoryDto categoryDto){
        return  new Category(categoryDto.getId() != null ? categoryDto.getId().longValue() : null, categoryDto.getName(), categoryDto.getDescription(), null);
    }
}
