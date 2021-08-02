package guru.springframework.api.v1.model.mapper;

import guru.springframework.domain.Customer;
import guru.springframework.model.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDTO(Customer customer);

    List<CustomerDTO> toDTOList(List<Customer> customers);

    Customer toEntity(CustomerDTO customerDTO);

}
