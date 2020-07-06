package com.seagalputra.tidder.web.controller;

import com.seagalputra.tidder.api.post.PostService;
import com.seagalputra.tidder.api.post.request.CreatePostRequest;
import com.seagalputra.tidder.api.web.GenericResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<GenericResponse> createPost(CreatePostRequest createPostRequest) {
        postService.save(createPostRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GenericResponse.SuccessResponse("Post successfully created!"));
    }

    @GetMapping
    public ResponseEntity<GenericResponse> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GenericResponse.SuccessResponse(postService.getAllPosts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GenericResponse.SuccessResponse(postService.getPost(id)));
    }

    @GetMapping("by-subreddit/{id}")
    public ResponseEntity<GenericResponse> getPostBySubreddit(Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GenericResponse.SuccessResponse(postService.getPostBySubreddit(id)));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<GenericResponse> getPostsByUsername(String username) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GenericResponse.SuccessResponse(postService.getPostByUsername(username)));
    }
}
