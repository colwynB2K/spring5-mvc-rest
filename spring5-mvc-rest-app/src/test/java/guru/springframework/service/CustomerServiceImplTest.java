package guru.springframework.service;

import guru.springframework.api.v1.model.mapper.CustomerMapper;
import guru.springframework.controller.v1.CustomerController;
import guru.springframework.domain.Customer;
import guru.springframework.model.CustomerDTO;
import guru.springframework.repository.CustomerRepository;
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
import static org.mockito.Mockito.verify;
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

    private CustomerDTO customerDTO;

    @BeforeEach
    public void setUp() {
        customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);
    }

    @Test
    void findAll() {

        //given
        when(customerRepository.findAll()).thenReturn(Arrays.asList(new Customer(), new Customer()));
        when(customerMapper.toDTO(any(Customer.class))).thenReturn(customerDTO);

        //when
        List<CustomerDTO> customerDTOS = customerServiceImpl.findAll();

        //then
        assertEquals(2, customerDTOS.size());
    }

    @Test
    void findById() {

        //given
        Customer customer1 = new Customer();
        customer1.setId(ID);
        customer1.setFirstname(FIRSTNAME);
        customer1.setLastname(LASTNAME);

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(customer1));
        when(customerMapper.toDTO(any(Customer.class))).thenReturn(customerDTO);

        //when
        CustomerDTO customerDTO = customerServiceImpl.findById(1L);

        assertEquals(FIRSTNAME, customerDTO.getFirstname());
    }

    @Test
    void create() {
        //given
        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(ID);

        when(customerMapper.toEntity(any(CustomerDTO.class))).thenReturn(savedCustomer);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(customerMapper.toDTO(any(Customer.class))).thenReturn(customerDTO);

        //when
        CustomerDTO savedDto = customerServiceImpl.create(customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
        assertEquals(customerDTO.getLastname(), savedDto.getLastname());
        assertEquals(CustomerController.URI + "/" + ID, savedDto.getUrl());
    }

    @Test
    void update() {

        //given
        customerDTO.setFirstname("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(ID);

        when(customerMapper.toEntity(any(CustomerDTO.class))).thenReturn(savedCustomer);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(customerMapper.toDTO(any(Customer.class))).thenReturn(customerDTO);

        //when
        CustomerDTO savedDto = customerServiceImpl.update(ID, customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
        assertEquals(CustomerController.URI + "/" + ID, savedDto.getUrl());
    }

    @Test
    void deleteById() {

        customerServiceImpl.deleteById(ID);

        verify(customerRepository).deleteById(anyLong());
    }
}