package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.service.CategoryService;
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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    public static final String CATEGORY_NAME = "Vegetables";
    public static final String CATEGORY_NAME2 = "Frozen";

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName(CATEGORY_NAME);
    }

    @Test
    void testListCategories() throws Exception {
        // given
        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(2L);
        categoryDTO2.setName(CATEGORY_NAME2);

        List<CategoryDTO> categoryDTOs = Arrays.asList(categoryDTO, categoryDTO2);

        when(categoryService.findAll()).thenReturn(categoryDTOs);

        // when
        mockMvc.perform(get("/api/v1/categories/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));

        // then
    }

    @Test
    void testGetByNameCategories() throws Exception {
        // given
        when(categoryService.findByName(anyString())).thenReturn(categoryDTO);

        // when
        mockMvc.perform(get("/api/v1/categories/" + CATEGORY_NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(CATEGORY_NAME)));

        // then
    }
}