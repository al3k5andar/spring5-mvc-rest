package guru.springfamework.services;

import guru.springfamework.api.v1.model.customer.CustomerDTO;

import java.util.List;

public interface CustomerService
{
    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);
}
