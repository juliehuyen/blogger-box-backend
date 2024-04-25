package com.dauphine.blogger.models;

import java.util.Date;
import java.util.UUID;

public class Post {

    private UUID id;
    private String title;
    private String content;
    private Date createdDate;
    private UUID categoryId;


    public Post(UUID id, String title, String content, UUID categoryId, Date createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.createdDate = createdDate;
    }

    public Object getId() {
        return id;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
