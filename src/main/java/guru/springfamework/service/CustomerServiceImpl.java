package guru.springfamework.service;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.mapper.CustomerMapper;
import guru.springfamework.controller.v1.CustomerController;
import guru.springfamework.domain.Customer;
import guru.springfamework.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> findAll() {

        return customerRepository
                .findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.toDTO(customer);
                    customerDTO.setCustomerUrl(CustomerController.URI + customer.getId());

                    return customerDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findById(Long id) {
        Customer customer = customerRepository.findById(id)
                                            .orElseThrow(RuntimeException::new); // TODO: Implement better exception handling
        return customerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);

        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnDto = customerMapper.toDTO(savedCustomer);
        returnDto.setCustomerUrl(CustomerController.URI + "/" + savedCustomer.getId());

        return returnDto;
    }
}
