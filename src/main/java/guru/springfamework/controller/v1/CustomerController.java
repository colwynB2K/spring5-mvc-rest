package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static guru.springfamework.controller.v1.CustomerController.URI;

@Controller
@Slf4j
@RequestMapping(URI)
public class CustomerController {

    public static final String URI = "/api/v1/customers";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> getAll() {
        return new ResponseEntity<>(new CustomerListDTO(customerService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<>(customerService.create(customerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<>(customerService.update(id, customerDTO), HttpStatus.OK);
    }
}