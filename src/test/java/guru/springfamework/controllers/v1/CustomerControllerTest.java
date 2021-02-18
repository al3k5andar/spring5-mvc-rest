package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.customer.CustomerDTO;
import guru.springfamework.services.CustomerService;
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

public class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    public static Long ID= 1L;
    public static String FIRST_NAME= "Joe";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc= MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void listCustomers() throws Exception {

//        Given
        CustomerDTO jim= new CustomerDTO();
        jim.setId(2L);
        jim.setFirstname("Jim");

        CustomerDTO joe= new CustomerDTO();
        joe.setId(ID);
        joe.setFirstname(FIRST_NAME);

        List<CustomerDTO> customerDTOList = Arrays.asList(joe, jim);

//        When
        Mockito.when(customerService.getAllCustomers()).thenReturn(customerDTOList);

//        Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.customers", Matchers.hasSize(2)));
    }

    @Test
    public void getCustomerById() throws Exception {

//        Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstname(FIRST_NAME);

//        When
        Mockito.when(customerService.getCustomerById(ID)).thenReturn(customerDTO);

//        Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstname",Matchers.equalTo(FIRST_NAME)));
    }

    @Test
    public void createNewCustomer() throws Exception {

//        Given
        CustomerDTO customer= new CustomerDTO();
        customer.setId(1L);
        customer.setFirstname("Jim");
        customer.setLastname("Simpson");

        CustomerDTO savedCustomer= new CustomerDTO();
        savedCustomer.setId(customer.getId());
        savedCustomer.setFirstname(customer.getFirstname());
        savedCustomer.setLastname(customer.getLastname());
        savedCustomer.setCustomer_url("/api/v1/customer/1");

//        When
        Mockito.when(customerService.createNewCustomer(customer)).thenReturn(savedCustomer);

//        Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AbstractRestControllerTest.asJsonString(customer)))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.firstname",Matchers.equalTo("Jim")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.customer_url",Matchers.equalTo("/api/v1/customer/1")));

    }
}