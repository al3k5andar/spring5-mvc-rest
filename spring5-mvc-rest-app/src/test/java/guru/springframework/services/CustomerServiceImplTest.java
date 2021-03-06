package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.customer.CustomerDTO;
import guru.springframework.controllers.v1.CustomerController;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    CustomerMapper customerMapper;

    CustomerServiceImpl customerService;

    public static Long ID= 1L;
    public static String NAME= "Joe";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerMapper= CustomerMapper.INSTANCE;

        customerService= new CustomerServiceImpl(customerRepository,customerMapper);
    }

    @Test
    public void getAllCustomers() {

//        Given
        List<Customer> customers= Arrays.asList(new Customer(),new Customer(), new Customer());

//        When
        Mockito.when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

//        Then
        Assert.assertEquals(3, customerDTOS.size());
    }

    @Test
    public void getCustomerById() {

//        Given
        Customer customer= new Customer();
        customer.setId(ID);
        customer.setFirstname(NAME);

//        When
        Mockito.when(customerRepository.findById(ID)).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.getCustomerById(ID);

//        Then
        Assert.assertEquals(ID, customerDTO.getId());
        Assert.assertEquals(NAME, customerDTO.getFirstname());
    }

    @Test
    public void createNewCustomer() {

//        Given
        CustomerDTO customerDTO= new CustomerDTO();
        customerDTO.setFirstname("Jim");
        customerDTO.setId(1L);

        Customer savedCustomer= new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(customerDTO.getId());

        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(savedCustomer);

//        When
        CustomerDTO savedDTO= customerService.createNewCustomer(customerDTO);

//        Then
        Assert.assertEquals(customerDTO.getFirstname(), savedDTO.getFirstname());
        Assert.assertEquals(CustomerController.BASE_URL+ "/1",savedDTO.getCustomer_url());
    }

    @Test
    public void updateCustomer() {

//        Given
        CustomerDTO customer= new CustomerDTO();
        customer.setFirstname(NAME);

        Customer savedCustomer= new Customer();
        savedCustomer.setFirstname(customer.getFirstname());
        savedCustomer.setLastname(customer.getLastname());
        savedCustomer.setId(ID);

        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(savedCustomer);

//        When
        CustomerDTO updatedCustomer= customerService.saveCustomerByDTO(ID, customer);

//        Then
        Assert.assertEquals(customer.getFirstname(), updatedCustomer.getFirstname());
        Assert.assertEquals(CustomerController.BASE_URL+ "/1", updatedCustomer.getCustomer_url());
    }

    @Test
    public void deleteCustomer() {

//        Given
        CustomerDTO customerDTO= new CustomerDTO();
        customerDTO.setId(ID);
        customerDTO.setFirstname(NAME);

//        When
        customerService.deleteCustomer(ID);

//        Then
        Mockito.verify(customerRepository,Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}