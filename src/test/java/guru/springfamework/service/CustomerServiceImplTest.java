package guru.springfamework.service;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.mapper.CustomerMapper;
import guru.springfamework.domain.Customer;
import guru.springfamework.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    public static final Long ID = 1L;
    public static final String FIRSTNAME = "Michael";
    public static final String LASTNAME = "Weston";

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void findAll() {

        //given
        when(customerRepository.findAll()).thenReturn(Arrays.asList(new Customer(), new Customer()));

        //when
        List<CustomerDTO> customerDTOS = customerServiceImpl.findAll();

        //then
        assertEquals(2, customerDTOS.size());
    }

    @Test
    void findByName() {

        //given
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstname(FIRSTNAME);
        customer1.setLastname(LASTNAME);

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(customer1));

        //when
        CustomerDTO customerDTO = customerServiceImpl.findById(1L);

        assertEquals(FIRSTNAME, customerDTO.getFirstname());
    }
}