package guru.springfamework.controller.v1;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;
import guru.springfamework.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static guru.springfamework.controller.v1.CategoryController.URI;

@RestController
@Slf4j
@RequestMapping(URI)
public class CategoryController {

    public static final String URI = "/api/v1/categories";
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public CategoryListDTO getAll() {
        return new CategoryListDTO(categoryService.findAll());
    }

    @GetMapping("/{name}")
    public CategoryDTO getByName(@PathVariable String name) {
        return categoryService.findByName(name);
    }
}
