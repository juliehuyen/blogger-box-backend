package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.CreationPostRequest;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.exceptions.PostNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("")
    @Operation(
            summary = "Create a new post",
            description = "Create a new post with the given title and content"
    )
    public ResponseEntity<Post> createPost(
            @Parameter(description = "Post")
            @RequestBody CreationPostRequest createPostRequest) throws CategoryNotFoundByIdException {
        Post post = postService.create(createPostRequest);
        return ResponseEntity
                .created(URI.create("v1/posts/" + post.getId()))
                .body(post);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get post by id",
            description = "Post by given id"
    )
    public ResponseEntity<Post> getPostById(
            @Parameter(description = "Id of the post")
            @PathVariable UUID id) throws PostNotFoundByIdException {
        Post post = postService.getById(id);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update post",
            description = "Update post by id"
    )
    public ResponseEntity<Post> updatePost(
            @Parameter(description = "Id of the post to be updated")
            @PathVariable UUID id,
            @Parameter(description = "Title of the post")
            @RequestBody String title,
            @Parameter(description = "Content of the post")
            @RequestBody String content) throws PostNotFoundByIdException {
        Post post = postService.update(id, title, content);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete post",
            description = "Delete post by id"
    )
    public ResponseEntity<?> deletePost(
            @Parameter(description = "Id of the post to delete")
            @PathVariable UUID id) throws PostNotFoundByIdException {
        postService.getById(id);
        postService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    @Operation(
            summary = "Retrieve all posts ordered by created_date endpoint ",
            description = "Returns a list of all posts filter like name or content"
    )
    public List<Post> retrieveAllPosts(@RequestParam(required = false)String value) {
        List<Post> posts = value == null || value.isBlank()
                ? postService.getAll()
                : postService.findAllPostByTitleOrContent(value,value);
        return posts;
    }

    @GetMapping("/categories/{id}/posts")
    @Operation(
            summary = "Retrieve all posts by a category",
            description = "Returns all posts by path variable"
    )
    public List<Post> retrievePostByCategory(
            @Parameter(description = "Category chosen")
            @PathVariable Category category) throws CategoryNotFoundByIdException {
        return postService.getAllByCategoryId(category);
    }

}
