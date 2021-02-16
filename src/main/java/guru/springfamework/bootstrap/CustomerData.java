package guru.springfamework.bootstrap;

import guru.springfamework.domain.Customer;

import java.util.Arrays;
import java.util.List;

public class CustomerData
{
    public static List<Customer> customers(){
        Customer jim= new Customer();
        jim.setId(1L);
        jim.setFirstName("Jim");
        jim.setLastName("Doe");

        Customer joe= new Customer();
        joe.setId(2L);
        joe.setFirstName("Joe");
        joe.setLastName("Simpson");

        Customer bob= new Customer();
        bob.setId(3L);
        bob.setFirstName("Bob");
        bob.setLastName("Woo");

        return Arrays.asList(jim,joe,bob);
    }
}
