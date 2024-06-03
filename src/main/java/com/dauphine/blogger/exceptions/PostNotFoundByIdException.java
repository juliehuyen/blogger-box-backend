package com.dauphine.blogger.exceptions;

public class PostNotFoundByIdException extends Exception {

    public PostNotFoundByIdException () {
        super("Post not found.");
    }
}
