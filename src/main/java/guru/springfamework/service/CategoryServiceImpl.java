package guru.springfamework.service;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.mapper.CategoryMapper;
import guru.springfamework.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryMapper.toDTOList(categoryRepository.findAll());
    }

    @Override
    public CategoryDTO findByName(String name) {
        return categoryMapper.toDTO(categoryRepository.findByName(name));
    }
}
