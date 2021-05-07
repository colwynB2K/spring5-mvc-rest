package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static guru.springfamework.controller.v1.CustomerController.URI;

@RestController
@Slf4j
@RequestMapping(URI)
public class CustomerController {

    public static final String URI = "/api/v1/customers";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public CustomerListDTO getAll() {
        return new CustomerListDTO(customerService.findAll());
    }

    @GetMapping("/{id}")
    public CustomerDTO getById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO create(@RequestBody CustomerDTO customerDTO){
        return customerService.create(customerDTO);
    }

    @PutMapping("/{id}")
    public CustomerDTO update(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        return customerService.update(id, customerDTO);
    }

    @PatchMapping("/{id}")
    public CustomerDTO patch(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        return customerService.patch(id, customerDTO);
    }

    @DeleteMapping({"/{id}"})
    public void deleteById(@PathVariable Long id){

        customerService.deleteById(id);
    }
}
