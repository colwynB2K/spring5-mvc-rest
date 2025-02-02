package guru.springframework.controller.v1;

import guru.springframework.controller.RestResponseEntityExceptionHandler;
import guru.springframework.exception.ResourceNotFoundException;
import guru.springframework.model.CustomerDTO;
import guru.springframework.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest extends AbstractRestControllerTest {

    public static final Long ID = 1L;
    public static final String FIRSTNAME = "Michael";
    public static final String LASTNAME = "Weston";

    @Mock
    private CustomerService mockCustomerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;
    private CustomerDTO customerDTO;
    private CustomerDTO returnDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();

        customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);
        customerDTO.setUrl(CustomerController.URI + "/" + ID);

        returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customerDTO.getFirstname());
        returnDTO.setLastname(customerDTO.getLastname());
        returnDTO.setUrl(CustomerController.URI + "/" + ID);
    }

    @Test
    void findAll() throws Exception {
        // given
        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstname(FIRSTNAME);
        customerDTO2.setLastname(LASTNAME);
        customerDTO2.setUrl(CustomerController.URI + "/2");

        List<CustomerDTO> customerDTOs = Arrays.asList(customerDTO, customerDTO2);

        when(mockCustomerService.findAll()).thenReturn(customerDTOs);

        // when
        mockMvc.perform(get(CustomerController.URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));

        // then
    }

    @Test
    void findById() throws Exception {
        // given
        when(mockCustomerService.findById(anyLong())).thenReturn(customerDTO);

        // when
        mockMvc.perform(get(CustomerController.URI + "/" + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)));

        // then
    }

    @Test
    void create() throws Exception {
        //given
        when(mockCustomerService.create(any())).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(post(CustomerController.URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.url", equalTo(CustomerController.URI + "/" + ID)));
    }

    @Test
    void update() throws Exception {
        //given
        customerDTO.setFirstname("Fred");
        customerDTO.setLastname("Flintstone");

        returnDTO.setFirstname("Fred");
        returnDTO.setLastname("Flintstone");
        when(mockCustomerService.update(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put(CustomerController.URI + "/" + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
                .andExpect(jsonPath("$.url", equalTo(CustomerController.URI + "/" + ID)));
    }

    @Test
    void patchCustomer() throws Exception {
        //given
        customerDTO.setFirstname("Fred");

        returnDTO.setFirstname(customerDTO.getFirstname());
        returnDTO.setLastname("Flintstone");

        when(mockCustomerService.patch(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(patch(CustomerController.URI + "/" + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
                .andExpect(jsonPath("$.url", equalTo(CustomerController.URI + "/" + ID)));
    }

    @Test
    void deleteById() throws Exception {

        mockMvc.perform(delete(CustomerController.URI + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(mockCustomerService).deleteById(anyLong());
    }

    @Test
    void notFoundException() throws Exception {

        when(mockCustomerService.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.URI + "/666")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}