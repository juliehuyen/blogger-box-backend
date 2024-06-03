package com.dauphine.blogger.exceptions;

public class CategoryNotFoundByIdException  extends Exception {

    public CategoryNotFoundByIdException () {
        super("Category not found.");
    }
}
