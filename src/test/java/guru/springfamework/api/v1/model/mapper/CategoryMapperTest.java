package guru.springfamework.api.v1.model.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    private static final Long CATEGORY_ID = 1L;
    private static final String CATEGORY_NAME = "Joe";
    private final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    void toDTO() {

        // given
        Category category = new Category();
        category.setName(CATEGORY_NAME);
        category.setId(CATEGORY_ID);

        // when
        CategoryDTO categoryDTO = categoryMapper.toDTO(category);

        // then
        assertEquals(CATEGORY_ID, categoryDTO.getId());
        assertEquals(CATEGORY_NAME, categoryDTO.getName());
    }
}