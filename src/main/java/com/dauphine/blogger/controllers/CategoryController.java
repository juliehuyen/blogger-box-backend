package com.dauphine.blogger.controllers;

import com.dauphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(
            summary = "Get all categories",
            description = "Retrieve all categories or filter like name"
    )
    public ResponseEntity<List<Category>> getAll(@RequestParam(required = false) String name) {
        List<Category> categories = categoryService.getAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get category by id",
            description = "Retrieve a category by id"
    )
    public ResponseEntity<Category> retrieveCategoryById(
            @Parameter(description = "Id of the category to retrieve")
            @PathVariable UUID id) throws CategoryNotFoundByIdException {
            Category category = categoryService.getById(id);
            return ResponseEntity.ok(category);
    }

    @PostMapping("")
    @Operation(
            summary = "Create a new category",
            description = "Creating a new category, and the only required field is the name of the category to create"
    )
    public ResponseEntity<Category> createCategory(
            @Parameter(description = "Name of the category")
            @RequestBody String name) throws CategoryNameAlreadyExistsException {
        Category category = categoryService.create(name);
        return ResponseEntity
                .created(URI.create("v1/categories/" + category.getId()))
                .body(category);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update category",
            description = "Update category by id"
    )
    public ResponseEntity<Category> updateCategory(
            @Parameter(description = "Id of the category to be updated")
            @PathVariable UUID id,
            @Parameter(description = "Name of the category")
            @RequestBody String name) throws CategoryNotFoundByIdException, CategoryNameAlreadyExistsException {
        Category category = categoryService.update(id, name);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete category",
            description = "Delete category by id"
    )
    public ResponseEntity<?> deleteCategory(
            @Parameter(description = "Id of the category to delete")
            @PathVariable UUID id) throws CategoryNotFoundByIdException {
        categoryService.getById(id);
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
