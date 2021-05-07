package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repository.CategoryRepository;
import guru.springfamework.repository.CustomerRepository;
import guru.springfamework.repository.VendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
// CommandLineRunner is a Spring Boot specific class that is called at startup
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public DataInitializer(CategoryRepository categoryRepository, CustomerRepository customerRepository,
                           VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
        loadVendors();
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        fruits.setName("Dried");

        Category fresh = new Category();
        fruits.setName("Fresh");

        Category exotic = new Category();
        fruits.setName("Exotic");

        Category nuts = new Category();
        fruits.setName("Nuts");

        categoryRepository.saveAll(Arrays.asList(fruits, dried, fresh, exotic, nuts));
        log.info("Categories loaded: " + categoryRepository.count());
    }

    private void loadCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstname("Michale");
        customer1.setLastname("Weston");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstname("Sam");
        customer2.setLastname("Axe");

        customerRepository.saveAll(Arrays.asList(customer1, customer2));
        log.info("Customers loaded: " + customerRepository.count());
    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Vendor 1 ");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Vendor 2 ");

        vendorRepository.saveAll(Arrays.asList(vendor1, vendor2));
    }

}
