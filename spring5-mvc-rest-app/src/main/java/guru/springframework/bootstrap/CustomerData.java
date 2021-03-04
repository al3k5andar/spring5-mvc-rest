package guru.springframework.bootstrap;

import guru.springframework.domain.Customer;

import java.util.Arrays;
import java.util.List;

public class CustomerData
{
    public static List<Customer> customers(){
        Customer jim= new Customer();
        jim.setId(1L);
        jim.setFirstname("Jim");
        jim.setLastname("Doe");

        Customer joe= new Customer();
        joe.setId(2L);
        joe.setFirstname("Joe");
        joe.setLastname("Simpson");

        Customer bob= new Customer();
        bob.setId(3L);
        bob.setFirstname("Bob");
        bob.setLastname("Woo");

        return Arrays.asList(jim,joe,bob);
    }
}
