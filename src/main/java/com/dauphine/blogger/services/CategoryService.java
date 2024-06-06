package com.dauphine.blogger.services;

import com.dauphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> getAll();
    Category getById(UUID id) throws CategoryNotFoundByIdException;
    Category create(String name) throws CategoryNameAlreadyExistsException;
    Category update(UUID id, String name) throws CategoryNotFoundByIdException, CategoryNameAlreadyExistsException;
    boolean deleteById(UUID id) throws CategoryNotFoundByIdException;
}
