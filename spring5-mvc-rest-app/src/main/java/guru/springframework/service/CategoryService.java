package guru.springframework.service;

import guru.springframework.api.v1.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAll();

    CategoryDTO findByName(String name);
}
