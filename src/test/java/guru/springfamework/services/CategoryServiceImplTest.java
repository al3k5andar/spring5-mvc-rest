package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.category.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    CategoryServiceImpl categoryService;

    public static final Long ID= 1L;
    public static final String NAME= "Joe";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoryService= new CategoryServiceImpl(categoryRepository,CategoryMapper.INSTANCE);
    }

    @Test
    public void getCategoryByName() {

//        Given
        Category category= new Category();
        category.setId(ID);
        category.setName(NAME);

//        When
        Mockito.when(categoryRepository.findByName(Mockito.anyString())).thenReturn(category);

        CategoryDTO categoryDTO= categoryService.getCategoryByName(NAME);

//        Then
        Assert.assertEquals(NAME,categoryDTO.getName());
        Assert.assertEquals(ID, categoryDTO.getId());
    }

    @Test
    public void getAllCategories() {

//        Given
        List<Category> categories= Arrays.asList(new Category(),new Category(),new Category());

        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

//        When
        List<CategoryDTO> categoryDTOS= categoryService.getAllCategories();

//        Then
        Assert.assertEquals(3, categoryDTOS.size());
    }
}