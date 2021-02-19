package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.category.CategoryDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.CategoryService;
import guru.springfamework.services.ResourceNotFoundException;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

public class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    public static final Long ID= 1L;
    public static final String NAME= "Joe";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc= MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                .build();
    }

    @Test
    public void listCategories() throws Exception {

//        Given
        CategoryDTO joe= new CategoryDTO();
        joe.setId(ID);
        joe.setName(NAME);

        CategoryDTO jim= new CategoryDTO();
        jim.setId(2L);
        jim.setName("Jim");

        List<CategoryDTO> categoryDTOList= Arrays.asList(jim,joe);

//        When
        Mockito.when(categoryService.getAllCategories()).thenReturn(categoryDTOList);

//        Then
        mockMvc.perform(MockMvcRequestBuilders.get(CategoryController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.categories", Matchers.hasSize(2)));
    }

    @Test
    public void getCategoryByName() throws Exception {

//        Given
        CategoryDTO joe= new CategoryDTO();
        joe.setId(ID);
        joe.setName(NAME);

//        When
        Mockito.when(categoryService.getCategoryByName(Mockito.anyString())).thenReturn(joe);

//        Then
        mockMvc.perform(MockMvcRequestBuilders.get(CategoryController.BASE_URL+ "/Joe")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name",Matchers.equalTo(NAME)));
    }

    @Test
    public void getCategoryByNameNotFound() throws Exception {

//        Give
        String name= "name";

//        When
        Mockito.when(categoryService.getCategoryByName(name)).thenThrow(ResourceNotFoundException.class);

//        Then
        mockMvc.perform(MockMvcRequestBuilders.get(CategoryController.BASE_URL+ name)
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}