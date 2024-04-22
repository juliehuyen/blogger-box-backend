package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.CreationCategoryRequest;
import com.dauphine.blogger.dto.CreationPostRequest;
import com.dauphine.blogger.dto.UpdateCategoryRequest;
import com.dauphine.blogger.dto.UpdatePostRequest;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final List<Post> temporaryPosts;

    public PostController() {
        temporaryPosts = new ArrayList<>();
        temporaryPosts.add(new Post(UUID.randomUUID(), "my first post"));
        temporaryPosts.add(new Post(UUID.randomUUID(), "my second post"));
        temporaryPosts.add(new Post(UUID.randomUUID(), "my third post"));
    }

    @PostMapping("")
    @Operation(
            summary = "Create a new post",
            description = "Create a new post with the given title and content"
    )
    public String createPost(
            @Parameter(description = "Post object to be created")
            @RequestBody CreationPostRequest post) {
        return "Creating a new post.";
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update post",
            description = "Update post by id"
    )
    public String updatePost(
            @Parameter(description = "Id of the post to be updated")
            @PathVariable UUID id,
            @Parameter(description = "Post object to be updated")
            @RequestBody UpdatePostRequest post) {
        return "Updating a post.";
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete post",
            description = "Delete post by id"
    )
    public String deletePost(
            @Parameter(description = "Id of the post to delete")
            @PathVariable UUID id) {
        return "Deleting a post.";
    }

    @GetMapping("")
    @Operation(
            summary = "Get all posts",
            description = "Retrieve all posts ordered by creation date"
    )
    public String retrieveAllPosts() {
        return "All posts.";
    }

}
