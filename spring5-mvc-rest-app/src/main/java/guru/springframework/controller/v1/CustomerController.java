package guru.springframework.controller.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.api.v1.model.CustomerListDTO;
import guru.springframework.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static guru.springframework.controller.v1.CustomerController.URI;

@RestController
// Customize controller section header for Swagger
@Tag(name = "Customer Controller", description = "This is the Customer controller")
@Slf4j
@RequestMapping(URI)
public class CustomerController {

    public static final String URI = "/api/v1/customers";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    // Customize endpoint operation section for Swagger
    @Operation(summary = "Get list of all customers", description = "This actually returns a list of CustomDTO objects.")
    public CustomerListDTO findAll() {
        return new CustomerListDTO(customerService.findAll());
    }

    @GetMapping("/{id}")
    public CustomerDTO findById(@PathVariable Long id) {
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
