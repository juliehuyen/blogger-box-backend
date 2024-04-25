package com.dauphine.blogger.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    public Post(UUID id, String title, String content, Category category, Date createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.createdDate = createdDate;
    }

    public Post() {
    }

    public Object getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
