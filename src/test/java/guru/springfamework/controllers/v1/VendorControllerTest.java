package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.vendor.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.VendorService;
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

public class VendorControllerTest {

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    public static Long ID= 1L;
    public static String NAME= "Fruit";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc= MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                .build();
    }

    @Test
    public void listVendors() throws Exception {

//        Given
        VendorDTO fruit= new VendorDTO();
        fruit.setId(ID);
        fruit.setName(NAME);

        VendorDTO vegetables= new VendorDTO();
        vegetables.setId(2L);
        vegetables.setName("Vegetables");

        List<VendorDTO> vendorDTOList= Arrays.asList(fruit, vegetables);

//        When
        Mockito.when(vendorService.getAllVendors()).thenReturn(vendorDTOList);

//        Then
        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(AbstractRestControllerTest.asJsonString(vendorDTOList)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.vendors",Matchers.hasSize(2)));
    }

    @Test
    public void getVendorById() throws Exception {

//        Given
        VendorDTO vendorDTO= new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);
        vendorDTO.setVendorUrl(VendorController.BASE_URL+ "/1");

//        When
        Mockito.when(vendorService.getVendorById(ID)).thenReturn(vendorDTO);

//        Then
        mockMvc.perform(MockMvcRequestBuilders.get(VendorController.BASE_URL+ "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name",Matchers.equalTo(NAME)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url",
                            Matchers.equalTo(VendorController.BASE_URL+"/1")));
    }

    @Test
    public void createNewVendor() throws Exception {

//        Given
        VendorDTO vendorDTO= new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);
        vendorDTO.setVendorUrl(VendorController.BASE_URL+ "/"+ID);

//        When
        Mockito.when(vendorService.createNewVendor(Mockito.any(VendorDTO.class))).thenReturn(vendorDTO);

//        Then
        mockMvc.perform(MockMvcRequestBuilders.post(VendorController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(AbstractRestControllerTest.asJsonString(vendorDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",Matchers.equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",Matchers.equalTo(NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url",
                        Matchers.equalTo(VendorController.BASE_URL+ "/1")));
    }

    @Test
    public void replaceVendor() throws Exception {

//        Given
        VendorDTO vendorDTO= new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);

        VendorDTO savedVendor= new VendorDTO();
        savedVendor.setId(vendorDTO.getId());
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setVendorUrl(VendorController.BASE_URL+"/"+vendorDTO.getId());

//        When
        Mockito.when(vendorService.replaceVendor(ID, vendorDTO)).thenReturn(savedVendor);

//        Then
        mockMvc.perform(MockMvcRequestBuilders.put(VendorController.BASE_URL+ "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(AbstractRestControllerTest.asJsonString(vendorDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id",Matchers.equalTo(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name",Matchers.equalTo(NAME)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url",
                            Matchers.equalTo(VendorController.BASE_URL+ "/1")));
    }

    @Test
    public void updateVendor() throws Exception {

//        Given
        VendorDTO vendorDTO= new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);

        VendorDTO updatedVendor= new VendorDTO();
        updatedVendor.setId(vendorDTO.getId());
        updatedVendor.setName(vendorDTO.getName());
        updatedVendor.setVendorUrl(VendorController.BASE_URL+"/1");

//        When
        Mockito.when(vendorService.updateVendor(ID,vendorDTO)).thenReturn(updatedVendor);

//        Then
        mockMvc.perform(MockMvcRequestBuilders.patch(VendorController.BASE_URL+"/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(AbstractRestControllerTest.asJsonString(vendorDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id",Matchers.equalTo(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name",Matchers.equalTo(NAME)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.vendor_url",
                            Matchers.equalTo(VendorController.BASE_URL+"/1")));
    }

    @Test
    public void deleteVendorById() throws Exception {

//        Given
        VendorDTO vendorDTO= new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(NAME);

//        When
        mockMvc.perform(MockMvcRequestBuilders.delete(VendorController.BASE_URL+ "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AbstractRestControllerTest.asJsonString(vendorDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk());

//        Then
        Mockito.verify(vendorService).deleteVendorById(Mockito.any());
    }
}