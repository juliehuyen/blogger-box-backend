package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.dto.CreationPostRequest;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.exceptions.PostNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.repositories.PostRepository;
import com.dauphine.blogger.services.PostService;
import com.dauphine.blogger.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;

    public PostServiceImpl(PostRepository repository, CategoryService categoryService) {
        this.postRepository = repository;
        this.categoryService = categoryService;
    }


    @Override
    public List<Post> getAllByCategoryId(Category category) throws CategoryNotFoundByIdException {
        categoryService.getById(category.getId());
        return postRepository.findByCategory(category);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public Post getById(UUID id) throws PostNotFoundByIdException {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundByIdException());
    }

    @Override
    public Post create(CreationPostRequest postRequest) throws CategoryNotFoundByIdException {
        Category category = categoryService.getById(postRequest.getCategoryId());
        Post post = new Post(UUID.randomUUID(), postRequest.getTitle(), postRequest.getContent(), category, new Date());
        return postRepository.save(post);
    }

    @Override
    public Post update(UUID id, String title, String content) throws PostNotFoundByIdException {
        Post post = getById(id);
        post.setTitle(title);
        post.setContent(content);
        return postRepository.save(post);
    }

    @Override
    public boolean deleteById(UUID id) throws PostNotFoundByIdException {
        getById(id);
        postRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Post> findAllPostByTitleOrContent(String title, String content) {
        return postRepository.findAllPostByTitleOrContent(title,content);
    }
}
