package com.dauphine.blogger.controllers;

import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

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
    public Post createPost(
            @Parameter(description = "Title of the post")
            @RequestBody String title,
            @Parameter(description = "Content of the post")
            @RequestBody String content,
            @Parameter(description = "Category of the post")
            @RequestBody Category category) {
        return postService.create(title, content, category);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update post",
            description = "Update post by id"
    )
    public Post updatePost(
            @Parameter(description = "Id of the post to be updated")
            @PathVariable UUID id,
            @Parameter(description = "Title of the post")
            @RequestBody String title,
            @Parameter(description = "Content of the post")
            @RequestBody String content) {
        return postService.update(id, title, content);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete post",
            description = "Delete post by id"
    )
    public void deletePost(
            @Parameter(description = "Id of the post to delete")
            @PathVariable UUID id) {
        postService.deleteById(id);
    }

    @GetMapping("")
    @Operation(
            summary = "Get all posts",
            description = "Retrieve all posts ordered by creation date"
    )
    public List<Post> retrieveAllPosts() {
        return postService.getAll();
    }

    @GetMapping("/categories/{id}/posts")
    @Operation(
            summary = "Retrieve all posts by a category",
            description = "Returns all posts by path variable"
    )
    public List<Post> retrievePostByCategory(
            @Parameter(description = "Category chosen")
            @PathVariable Category category) {
        return postService.getAllByCategoryId(category);
    }

}
