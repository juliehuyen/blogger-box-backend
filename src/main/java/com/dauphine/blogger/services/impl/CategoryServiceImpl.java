package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.repositories.CategoryRepository;
import com.dauphine.blogger.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.categoryRepository = repository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(UUID id) throws CategoryNotFoundByIdException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundByIdException());
    }

    @Override
    public Category create(String name) throws CategoryNameAlreadyExistsException {
        if (categoryRepository.findByName(name) == null) {
            Category category = new Category(name);
            return categoryRepository.save(category);
        }
        else {
            throw new CategoryNameAlreadyExistsException();
        }
    }

    @Override
    public Category update(UUID id, String newName) throws CategoryNotFoundByIdException, CategoryNameAlreadyExistsException {
        Category category = getById(id);
        if (category == null) {
            throw new CategoryNotFoundByIdException();
        }
        if (categoryRepository.findByName(newName) != null) {
            throw new CategoryNameAlreadyExistsException();
        }
        category.setName(newName);
        return categoryRepository.save(category);
    }

    @Override
    public boolean deleteById(UUID id) throws CategoryNotFoundByIdException {
        getById(id);
        categoryRepository.deleteById(id);
        return true;
    }

}
