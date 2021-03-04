package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.customer.CustomerDTO;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplITTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;


    @Before
    public void setUp() throws Exception {
        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());

//        Loading data from Bootstrap class
        Bootstrap data= new Bootstrap(categoryRepository,customerRepository, vendorRepository);
        data.run();

        customerService= new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void patchCustomerUpdateFirstName(){
        String updatedName= "Updated name";
        long id= getCustomerIdValue();

        Customer originalCustomer= customerRepository.getOne(id);
        Assert.assertNotNull(originalCustomer);

        String originalFirstName= originalCustomer.getFirstname();
        String originalLastName= originalCustomer.getLastname();

        CustomerDTO customerDTO= new CustomerDTO();
        customerDTO.setFirstname(updatedName);

        customerService.patchCustomer(id,customerDTO);

        Customer updatedCustomer= customerRepository.findById(id).get();

        Assert.assertNotNull(updatedCustomer);
        Assert.assertEquals(updatedName, updatedCustomer.getFirstname());
        Assert.assertThat(originalFirstName, Matchers.not(Matchers.equalTo(updatedCustomer.getFirstname())));
        Assert.assertThat(originalLastName, Matchers.equalTo(updatedCustomer.getLastname()));
    }

    @Test
    public void patchCustomerUpdateLastName(){
        String updatedLastName= "UpdatedLastName";
        long id= getCustomerIdValue();

        Customer original= customerRepository.getOne(id);
        Assert.assertNotNull(original);

        String originalFirstName= original.getFirstname();
        String originalLastName= original.getLastname();

        CustomerDTO customerDTO= new CustomerDTO();
        customerDTO.setLastname(updatedLastName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer= customerRepository.findById(id).get();

        Assert.assertNotNull(updatedCustomer);
        Assert.assertEquals(updatedLastName, updatedCustomer.getLastname());
        Assert.assertThat(originalFirstName, Matchers.equalTo(updatedCustomer.getFirstname()));
        Assert.assertThat(originalLastName, Matchers.not(Matchers.equalTo(updatedCustomer.getLastname())));

    }

    private Long getCustomerIdValue(){
        List<Customer> customers= customerRepository.findAll();

        System.out.println("Customers found: "+ customers.size());

        return customers.get(0).getId();
    }
}