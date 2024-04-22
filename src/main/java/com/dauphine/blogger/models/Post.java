package com.dauphine.blogger.models;

import java.util.Date;
import java.util.UUID;

public class Post {

    private UUID id;
    private String title;
    private String content;
    private Date createdDate;
    private UUID category;


    public Post(UUID id, String title) {
        this.id = id;
        this.title = title;
    }
}
