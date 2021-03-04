package guru.springframework.services;

import guru.springframework.api.v1.model.category.CategoryDTO;

import java.util.List;

public interface CategoryService
{
    CategoryDTO getCategoryByName(String name);

    List<CategoryDTO> getAllCategories();
}
