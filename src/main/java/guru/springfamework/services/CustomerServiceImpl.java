package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.customer.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> {
                    CustomerDTO customerDTO= customerMapper.customerToCustomerDAO(customer);
                    customerDTO.setCustomer_url("/api/v1/customers/"+ customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    CustomerDTO customerDTO= customerMapper.customerToCustomerDAO(customer);
                    customerDTO.setCustomer_url("/api/v1/customers/"+ customer.getId());
                    return customerDTO;
                })
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(customerMapper.customerDTOtoCustomer(customerDTO));
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer= customerMapper.customerDTOtoCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
            if(customerDTO.getFirstname()!= null)
                customer.setFirstname(customerDTO.getFirstname());
            if(customerDTO.getLastname()!= null)
                customer.setLastname(customerDTO.getLastname());

            CustomerDTO returnDto= customerMapper.customerToCustomerDAO(customerRepository.save(customer));
            returnDto.setCustomer_url("/api/v1/customers"+ customer.getId());
            return returnDto;
        }).orElseThrow(RuntimeException::new);
    }

    private CustomerDTO saveAndReturnDTO(Customer customer){

        Customer savedCustomer= customerRepository.save(customer);

        CustomerDTO savedDTO= customerMapper.customerToCustomerDAO(savedCustomer);
        savedDTO.setCustomer_url("/api/v1/customer/"+savedCustomer.getId());

        return savedDTO;
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
