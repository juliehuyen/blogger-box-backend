package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.repositories.PostRepository;
import com.dauphine.blogger.services.PostService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository repository) {
        this.postRepository = repository;
    }


    @Override
    public List<Post> getAllByCategoryId(Category category) {
        return postRepository.findByCategory(category);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public Post getById(UUID id) {
        return postRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Post create(String title, String content, Category category) {
        Post post = new Post(UUID.randomUUID(), title, content, category, new Date());
        return postRepository.save(post);
    }

    @Override
    public Post update(UUID id, String title, String content) {
        Post post = getById(id);
        if (post == null) {
            return null;
        }
        post.setTitle(title);
        post.setContent(content);
        return postRepository.save(post);
    }

    @Override
    public boolean deleteById(UUID id) {
        postRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Post> findAllPostByTitleOrContent(String title, String content) {
        return postRepository.findAllPostByTitleOrContent(title,content);
    }
}
