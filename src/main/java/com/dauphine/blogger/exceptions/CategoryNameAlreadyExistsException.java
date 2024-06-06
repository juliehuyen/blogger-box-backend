package com.dauphine.blogger.exceptions;

public class CategoryNameAlreadyExistsException extends Exception {

    public CategoryNameAlreadyExistsException() {
        super("Category already exists.");
    }
}
