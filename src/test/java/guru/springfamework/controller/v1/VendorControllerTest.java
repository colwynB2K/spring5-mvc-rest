package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.controller.RestResponseEntityExceptionHandler;
import guru.springfamework.service.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static guru.springfamework.controller.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
//@WebMvcTest(controllers = {VendorController.class})
class VendorControllerTest {

    @Mock
    VendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    private MockMvc mockMvc;

    private VendorDTO vendorDTO_1;
    private VendorDTO vendorDTO_2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();

        vendorDTO_1 = new VendorDTO("Vendor 1", VendorController.URI + "/1");
        vendorDTO_2 = new VendorDTO("Vendor 2", VendorController.URI + "/2");
    }

    @Test
    void findAll() throws Exception {
        List<VendorDTO> vendorDTOS = Arrays.asList(vendorDTO_1, vendorDTO_2);

        when(vendorService.findAll()).thenReturn(vendorDTOS);

        mockMvc.perform(get(VendorController.URI)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    void findById() throws Exception {

        when(vendorService.findById(anyLong())).thenReturn(vendorDTO_1);

        mockMvc.perform(get(VendorController.URI + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    void create() throws Exception {

        when(vendorService.create(vendorDTO_1)).thenReturn(vendorDTO_1);

        mockMvc.perform(post(VendorController.URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO_1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    void update() throws Exception {

        when(vendorService.update(anyLong(), any(VendorDTO.class))).thenReturn(vendorDTO_1);

        mockMvc.perform(put(VendorController.URI + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    void patchVendor() throws Exception {
        when(vendorService.patch(anyLong(), any(VendorDTO.class))).thenReturn(vendorDTO_1);

        mockMvc.perform(patch(VendorController.URI + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(VendorController.URI + "/1"))
                .andExpect(status().isOk());

        verify(vendorService).deleteById(anyLong());
    }
}