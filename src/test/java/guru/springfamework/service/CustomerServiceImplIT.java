package guru.springfamework.service;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.mapper.CustomerMapper;
import guru.springfamework.bootstrap.DataInitializer;
import guru.springfamework.domain.Customer;
import guru.springfamework.repository.CategoryRepository;
import guru.springfamework.repository.CustomerRepository;
import guru.springfamework.repository.VendorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CustomerServiceImplIT {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;

    @BeforeEach
    public void setUp() throws Exception {
        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());

        //setup data for testing
        DataInitializer dataInitializer = new DataInitializer(categoryRepository, customerRepository, vendorRepository);
        dataInitializer.run(); //load data

        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    void patchUpdateFirstName() {

        // given
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        // store original first and last name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        // Create a CustomerDTO which only contains the firstName value we want to update
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updatedName);

        customerService.patch(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstname());
        assertNotEquals(originalFirstName, updatedCustomer.getFirstname());
        assertEquals(originalLastName,updatedCustomer.getLastname());
    }

    @Test
    void patchCustomerUpdateLastName() {

        // given
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        // store original first and last name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        // Create a CustomerDTO which only contains the lastName value we want to update
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(updatedName);

        customerService.patch(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastname());
        assertNotEquals(originalLastName, updatedCustomer.getLastname());
        assertEquals(originalFirstName,updatedCustomer.getFirstname());
    }

    private Long getCustomerIdValue(){
        List<Customer> customers = customerRepository.findAll();

        System.out.println("Customers Found: " + customers.size());

        //return first id
        return customers.get(0).getId();
    }
}
