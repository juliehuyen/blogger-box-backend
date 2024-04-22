package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.CreationCategoryRequest;
import com.dauphine.blogger.dto.UpdateCategoryRequest;
import com.dauphine.blogger.models.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final List<Category> temporaryCategories;

    public CategoryController() {
        temporaryCategories = new ArrayList<>();
        temporaryCategories.add(new Category(UUID.randomUUID(), "my first category"));
        temporaryCategories.add(new Category(UUID.randomUUID(), "my second category"));
        temporaryCategories.add(new Category(UUID.randomUUID(), "my third category"));
    }

    @GetMapping("")
    @Operation(
            summary = "Retrieve all categories",
            description = "Returns all the categories"
    )
    public String retrieveAllCategories() {
        return "All categories.";
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve a category by id",
            description = "Returns a category by path variable"
    )
    public String retrieveCategoryById(
            @Parameter(description = "Id of the category to retrieve")
            @PathVariable UUID id) {
        return "Category " + id;
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
    public String createCategory(
            @Parameter(description = "Category object to be created")
            @RequestBody CreationCategoryRequest category) {
        return "Creating a new category.";
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update category",
            description = "Update category by id"
    )
    public String updateCategory(
            @Parameter(description = "Id of the category to be updated")
            @PathVariable UUID id,
            @Parameter(description = "Category object to be updated")
            @RequestBody UpdateCategoryRequest category) {
        return "Updating a category.";
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete category",
            description = "Delete category by id"
    )
    public String deleteCategory(
            @Parameter(description = "Id of the category to delete")
            @PathVariable UUID id) {
        return "Deleting a category.";
    }

}
