package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.category.CategoryDTO;
import guru.springframework.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

// Better way is to create manuel convertor for DTO
// For this is required to many maven dependencies

@Mapper
public interface CategoryMapper
{
    CategoryMapper INSTANCE= Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "id",target = "id")
    CategoryDTO categoryToCategoryDTO(Category category);
}
