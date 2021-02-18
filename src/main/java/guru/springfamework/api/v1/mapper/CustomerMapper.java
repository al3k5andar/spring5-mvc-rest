package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.customer.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE= Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "id",target = "id")
    CustomerDTO customerToCustomerDAO(Customer customer);

    Customer customerDTOtoCustomer(CustomerDTO customerDTO);
}
