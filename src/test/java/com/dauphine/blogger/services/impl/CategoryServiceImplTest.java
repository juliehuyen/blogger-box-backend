package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.repositories.CategoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    private CategoryRepository categoryRepository;
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp(){
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void shouldCreateCategory() throws CategoryNameAlreadyExistsException {
        String name = "new category";
        Category expectedCategory = new Category(name);
        when(categoryRepository.existsByName(name)).thenReturn(false);
        when(categoryRepository.save(any())).thenReturn(expectedCategory);

        Category actualCategory = categoryService.create(name);
        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    void shouldThrowExceptionWhenCreateCategoryWithAlreadyExists() {
        String name = "new category";
        Category expectedCategory = new Category(name);
        when(categoryRepository.findByName(name)).thenReturn(expectedCategory);

        CategoryNameAlreadyExistsException exception = assertThrows(
                CategoryNameAlreadyExistsException.class,
                () -> categoryService.create(name)
        );
        assertThrows(CategoryNameAlreadyExistsException.class, ()->categoryService.create(name));
        assertEquals("Category already exists.", exception.getMessage());
    }

    @Test
    void shouldReturnCategoryWhenGetById() throws CategoryNotFoundByIdException {
        UUID id = UUID.randomUUID();
        Category expected =  new Category("Category");
        when(categoryRepository.findById(id)).thenReturn(Optional.of(expected));
        Category actual = categoryService.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWhenNotFoundById() {
        UUID id = UUID.randomUUID();
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        CategoryNotFoundByIdException exception = assertThrows(
                CategoryNotFoundByIdException.class,
                () -> categoryService.getById(id)
        );
        assertThrows(CategoryNotFoundByIdException.class, ()->categoryService.getById(id));
        assertEquals("Category not found.", exception.getMessage());
    }

    @Test
    void shouldReturnAllCategoriesWhenGetAll(){
        List<Category> expectedCategories = List.of(new Category("Category1"),
                new Category("Category2"),
                new Category("Category3"));
        when(categoryRepository.findAll()).thenReturn(expectedCategories);
        List<Category> actualCategories = categoryService.getAll();

        assertEquals(expectedCategories.size(), actualCategories.size());
        verify(categoryRepository).findAll();
    }

    @Test
    void shouldUpdateCategory() throws CategoryNotFoundByIdException, CategoryNameAlreadyExistsException {
        UUID id = UUID.randomUUID();
        String oldName = "Old";
        String expectedName = "Updated";
        Category oldCategory = new Category(oldName);
        Category expectedCategory = new Category(expectedName);
        when(categoryRepository.findByName(oldName)).thenReturn(oldCategory);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(oldCategory));
        when(categoryRepository.save(any())).thenReturn(expectedCategory);

        Category actualCategory = categoryService.update(id,expectedName);
        assertEquals(expectedCategory.getName(),actualCategory.getName());
    }

    @Test
    void shouldThrowCategoryNotFoundExceptionWhenUpdate() {
        UUID id = UUID.randomUUID();
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        CategoryNotFoundByIdException thrownException = assertThrows(
                CategoryNotFoundByIdException.class,
                () -> categoryService.update(id,"new name")
        );

        assertEquals("Category not found.", thrownException.getMessage());
    }

    @Test
    void shouldThrowCategoryNameAlreadyExistsExceptionWhenUpdate()  {
        UUID id = UUID.randomUUID();
        String name = "nameAlreadyExists";
        Category category = new Category("old name");
        Category category2 = new Category(name);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryRepository.findByName(name)).thenReturn(category2);

        CategoryNameAlreadyExistsException thrownException = assertThrows(
                CategoryNameAlreadyExistsException.class,
                () -> categoryService.update(id,name)
        );

        assertEquals("Category already exists.", thrownException.getMessage());
    }

    @Test
    void shouldDeleteWhenDelete() throws CategoryNotFoundByIdException {
        UUID id = UUID.randomUUID();
        Category category = new Category("cat");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).deleteById(id);

        categoryService.deleteById(id);

        verify(categoryRepository).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenDelete(){
        UUID id = UUID.randomUUID();

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());
        CategoryNotFoundByIdException thrownException = assertThrows(
                CategoryNotFoundByIdException.class,
                () -> categoryService.deleteById(id)
        );

        assertEquals("Category not found.", thrownException.getMessage());
    }

}