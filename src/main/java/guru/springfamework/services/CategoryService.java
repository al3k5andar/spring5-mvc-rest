package guru.springfamework.services;

import guru.springfamework.api.v1.model.category.CategoryDTO;

import java.util.List;

public interface CategoryService
{
    CategoryDTO getCategoryByName(String name);

    List<CategoryDTO> getAllCategories();
}
