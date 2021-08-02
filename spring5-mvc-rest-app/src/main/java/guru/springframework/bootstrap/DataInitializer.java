package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Vendor;
import guru.springframework.repository.CategoryRepository;
import guru.springframework.repository.CustomerRepository;
import guru.springframework.repository.VendorRepository;
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
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

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
