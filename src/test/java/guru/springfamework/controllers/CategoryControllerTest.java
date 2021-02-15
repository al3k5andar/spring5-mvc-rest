package guru.springfamework.controllers;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.services.CategoryService;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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

        mockMvc= MockMvcBuilders.standaloneSetup(categoryController).build();
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
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/")
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
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/Joe")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name",Matchers.equalTo(NAME)));
    }
}