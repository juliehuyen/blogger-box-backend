package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.CreationCategoryRequest;
import com.dauphine.blogger.dto.UpdateCategoryRequest;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    @Operation(
            summary = "Retrieve all categories",
            description = "Returns all the categories"
    )
    public List<Category> retrieveAllCategories() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve a category by id",
            description = "Returns a category by path variable"
    )
    public Category retrieveCategoryById(
            @Parameter(description = "Id of the category to retrieve")
            @PathVariable UUID id) {
        return categoryService.getById(id);
    }

    @GetMapping("/{id}/posts")
    @Operation(
            summary = "Retrieve all posts by a category",
            description = "Returns all posts by path variable"
    )
    public String retrievePostByCategory(
            @Parameter(description = "Id of the category")
            @PathVariable UUID id) {
        return "Posts of " + id;
    }

    @PostMapping("")
    @Operation(
            summary = "Create a new category",
            description = "Creating a new category"
    )
    public Category createCategory(
            @Parameter(description = "Category object to be created")
            @RequestBody String name) {
        return categoryService.create(name);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update category",
            description = "Update category by id"
    )
    public Category updateCategory(
            @Parameter(description = "Id of the category to be updated")
            @PathVariable UUID id,
            @Parameter(description = "Category object to be updated")
            @RequestBody String name) {
        return categoryService.update(id, name);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete category",
            description = "Delete category by id"
    )
    public void deleteCategory(
            @Parameter(description = "Id of the category to delete")
            @PathVariable UUID id) {
        categoryService.deleteById(id);
    }

}
