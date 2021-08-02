package guru.springframework.service;

import guru.springframework.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> findAll();

    CustomerDTO findById(Long id);

    CustomerDTO create(CustomerDTO customerDTO);

    CustomerDTO update(Long id, CustomerDTO customerDTO);

    CustomerDTO patch(Long id, CustomerDTO customerDTO);

    void deleteById(Long id);
}
