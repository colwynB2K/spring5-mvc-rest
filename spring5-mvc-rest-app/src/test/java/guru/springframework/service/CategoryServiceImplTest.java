package guru.springframework.service;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.mapper.CategoryMapper;
import guru.springframework.domain.Category;
import guru.springframework.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    public static final Long ID = 2L;
    public static final String NAME = "Vegetables";

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void findAll() {

        //given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        List<CategoryDTO> categoryDTOs = Arrays.asList(new CategoryDTO(), new CategoryDTO(), new CategoryDTO());

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.toDTOList(any(List.class))).thenReturn(categoryDTOs);

        //when
        List<CategoryDTO> actualCategoryDTOs = categoryServiceImpl.findAll();

        //then
        assertEquals(3, actualCategoryDTOs.size());
    }

    @Test
    void findByName() {

        //given
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(ID);
        categoryDTO.setName(NAME);

        when(categoryRepository.findByName(anyString())).thenReturn(category);
        when(categoryMapper.toDTO(any(Category.class))).thenReturn(categoryDTO);

        //when
        CategoryDTO actualCategoryDTO = categoryServiceImpl.findByName(NAME);

        //then
        assertEquals(ID, actualCategoryDTO.getId());
        assertEquals(NAME, actualCategoryDTO.getName());
    }

}