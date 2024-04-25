package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final List<Post> temporaryPosts;

    public PostServiceImpl() {
        Category temporaryCategory = new Category(UUID.randomUUID(), "my first category");
        temporaryPosts = new ArrayList<>();
        temporaryPosts.add(new Post(UUID.randomUUID(), "my first post", "content", temporaryCategory, new Date()));
        temporaryPosts.add(new Post(UUID.randomUUID(), "my first post", "content", temporaryCategory, new Date()));
        temporaryPosts.add(new Post(UUID.randomUUID(), "my first post", "content", temporaryCategory, new Date()));
    }


    @Override
    public List<Post> getAllByCategoryId(Category category) {
        return temporaryPosts.stream()
                .filter(post -> category.equals(post.getCategory()))
                .toList();
    }

    @Override
    public List<Post> getAll() {
        return temporaryPosts;
    }

    @Override
    public Post getById(UUID id) {
        return temporaryPosts.stream()
                .filter(post -> id.equals(post.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Post create(String title, String content, Category category) {
        Post post = new Post(UUID.randomUUID(), title, content, category, new Date());
        temporaryPosts.add(post);
        return post;
    }

    @Override
    public Post update(UUID id, String title, String content) {
        Post post = temporaryPosts.stream()
                .filter(c -> id.equals(c.getId()))
                .findFirst()
                .orElse(null);
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
        }
        return post;
    }

    @Override
    public void deleteById(UUID id) {
        temporaryPosts.removeIf(post -> id.equals(post.getId()));
    }
}
