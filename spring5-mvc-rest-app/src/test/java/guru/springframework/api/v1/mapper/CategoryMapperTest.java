package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.category.CategoryDTO;
import guru.springframework.domain.Category;
import org.junit.Assert;
import org.junit.Test;

public class CategoryMapperTest {

    CategoryMapper categoryMapper= CategoryMapper.INSTANCE;

    public static final Long ID= 1L;
    public static final String NAME= "Joe";


    @Test
    public void categoryToCategoryDTO() {

//        Given
        Category category= new Category();
        category.setId(ID);
        category.setName(NAME);

//        When
        CategoryDTO categoryDTO= categoryMapper.categoryToCategoryDTO(category);

//        Then
        Assert.assertEquals(ID, categoryDTO.getId());
        Assert.assertEquals(NAME, categoryDTO.getName());
    }
}