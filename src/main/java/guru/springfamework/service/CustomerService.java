package guru.springfamework.service;

import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> findAll();

    CustomerDTO findById(Long id);

    CustomerDTO create(CustomerDTO customerDTO);

    CustomerDTO update(Long id, CustomerDTO customerDTO);
}
