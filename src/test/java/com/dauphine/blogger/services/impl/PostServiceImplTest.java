package com.dauphine.blogger.services.impl;

import com.dauphine.blogger.dto.CreationPostRequest;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.exceptions.PostNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.repositories.PostRepository;
import com.dauphine.blogger.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PostServiceImplTest {

    private PostRepository postRepository;
    private PostServiceImpl postService;

    @BeforeEach
    void setUp(){
        postRepository = mock(PostRepository.class);
        CategoryService categoryService = mock(CategoryService.class);
        postService = new PostServiceImpl(postRepository, categoryService);
    }

    @Test
    void shouldReturnAllPosts() {
        List<Post> expectedPosts = List.of(new Post(), new Post());
        when(postRepository.findAll()).thenReturn(expectedPosts);
        List<Post> actualPosts = postService.getAll();

        assertEquals(expectedPosts.size(), actualPosts.size());
        verify(postRepository).findAll();
    }

    @Test
    void shouldReturnPostWhenGetById() throws PostNotFoundByIdException {
        UUID id = UUID.randomUUID();
        Category category = new Category("Category");
        Post expected = new Post(id, "Title1", "Content1", category, null);
        when(postRepository.findById(id)).thenReturn(Optional.of(expected));
        Post actual = postService.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnAllPostsByCategoryId() throws CategoryNotFoundByIdException {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        Category category = new Category("Category");
        List<Post> expectedPosts = List.of(
                new Post(id1,"Title1","Content1",category,null),
                new Post(id2,"Title2","Content2",category,null));
        when(postRepository.findByCategory(category)).thenReturn(expectedPosts);
        List<Post> actualPosts = postService.getAllByCategoryId(category);
        assertEquals(expectedPosts.size(), actualPosts.size());
        verify(postRepository).findByCategory(category);
    }

    @Test
    void shouldCreatePost() throws CategoryNotFoundByIdException {
        UUID id = UUID.randomUUID();
        Category category = new Category("Category");
        Post expectedPost = new Post(id, "Title", "Content", category, null);
        when(postRepository.existsById(id)).thenReturn(false);
        when(postRepository.save(any())).thenReturn(expectedPost);

        CreationPostRequest postRequest = new CreationPostRequest();
        Post actualPost = postService.create(postRequest);
        assertEquals(expectedPost, actualPost);
    }

    @Test
    void shouldUpdatePost() throws PostNotFoundByIdException {
        UUID id = UUID.randomUUID();
        Category category = new Category("Category");
        Post oldPost = new Post(id, "Title", "Content", category, null);
        when(postRepository.findById(id)).thenReturn(Optional.of(oldPost));
        when(postRepository.save(any())).thenReturn(oldPost);

        String title = "New Title";
        String content = "New Content";
        Post actualPost = postService.update(id, title, content);
        assertEquals(title, actualPost.getTitle());
        assertEquals(content, actualPost.getContent());
    }

    @Test
    void shouldDeletePost() throws PostNotFoundByIdException {
        UUID id = UUID.randomUUID();
        Post post = new Post(id, "Title", "Content", new Category("Category"), null);
        when(postRepository.findById(id)).thenReturn(Optional.of(post));
        doNothing().when(postRepository).deleteById(id);
        postService.deleteById(id);
        verify(postRepository).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenNotFoundById() {
        UUID id = UUID.randomUUID();
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        PostNotFoundByIdException exception = assertThrows(
                PostNotFoundByIdException.class,
                () -> postService.getById(id)
        );
        assertThrows(PostNotFoundByIdException.class, ()->postService.getById(id));
        assertEquals("Post not found.", exception.getMessage());
    }

    @Test
    void shouldThrowPostNotFoundExceptionWhenUpdate() {
        UUID id = UUID.randomUUID();
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        PostNotFoundByIdException exception = assertThrows(
                PostNotFoundByIdException.class,
                () -> postService.update(id, "Title", "Content")
        );

        assertEquals("Post not found.", exception.getMessage());
        assertThrows(PostNotFoundByIdException.class, ()->postService.update(id, "Title", "Content"));
    }

    @Test
    void shouldThrowExceptionWhenDelete() {
        UUID id = UUID.randomUUID();
        when(postRepository.findById(id)).thenReturn(Optional.empty());

        PostNotFoundByIdException exception = assertThrows(
                PostNotFoundByIdException.class,
                () -> postService.deleteById(id)
        );

        assertThrows(PostNotFoundByIdException.class, ()->postService.deleteById(id));
        assertEquals("Post not found.", exception.getMessage());
    }
}
