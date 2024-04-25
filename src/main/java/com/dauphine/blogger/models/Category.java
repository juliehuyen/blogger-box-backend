package com.dauphine.blogger.models;

import java.util.UUID;

public class Category {

    private UUID id;
    private String name;

    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String newName) {
        name = newName;
    }
}
