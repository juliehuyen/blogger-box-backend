package com.dauphine.blogger.services.impl;

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
    public Category getById(UUID id) {
        return categoryRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Category create(String name) {
        Category category = new Category(UUID.randomUUID(), name);
        return categoryRepository.save(category);
    }

    @Override
    public Category update(UUID id, String newName) {
        Category category = getById(id);
        if (category == null) {
            return null;
        }
        category.setName(newName);
        return categoryRepository.save(category);
    }

    @Override
    public boolean deleteById(UUID id) {
        categoryRepository.deleteById(id);
        return true;
    }
}
