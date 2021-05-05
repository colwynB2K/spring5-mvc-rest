package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.service.CustomerService;
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
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;
    private CustomerDTO customerDTO;
    private CustomerDTO returnDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);
        customerDTO.setCustomerUrl(CustomerController.URI + "/" + ID);

        returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customerDTO.getFirstname());
        returnDTO.setLastname(customerDTO.getLastname());
        returnDTO.setCustomerUrl(CustomerController.URI + "/" + ID);
    }

    @Test
    void getAll() throws Exception {
        // given
        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstname(FIRSTNAME);
        customerDTO2.setLastname(LASTNAME);
        customerDTO2.setCustomerUrl(CustomerController.URI + "/2");

        List<CustomerDTO> customerDTOs = Arrays.asList(customerDTO, customerDTO2);

        when(customerService.findAll()).thenReturn(customerDTOs);

        // when
        mockMvc.perform(get(CustomerController.URI)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));

        // then
    }

    @Test
    void getById() throws Exception {
        // given
        when(customerService.findById(anyLong())).thenReturn(customerDTO);

        // when
        mockMvc.perform(get(CustomerController.URI + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)));

        // then
    }

    @Test
    void create() throws Exception {
        //given
        when(customerService.create(customerDTO)).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(post(CustomerController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.URI + "/" + ID)));
    }

    @Test
    void update() throws Exception {
        //given
        customerDTO.setFirstname("Fred");
        customerDTO.setLastname("Flintstone");

        returnDTO.setFirstname("Fred");
        returnDTO.setLastname("Flintstone");
        when(customerService.update(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put(CustomerController.URI + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.URI + "/" + ID)));
    }
}