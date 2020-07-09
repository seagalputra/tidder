package com.seagalputra.tidder.web.controller;

import com.seagalputra.tidder.api.comment.CommentService;
import com.seagalputra.tidder.api.comment.request.CreateCommentRequest;
import com.seagalputra.tidder.api.web.GenericResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentsController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<GenericResponse> createComment(@RequestBody CreateCommentRequest createCommentRequest) {
        commentService.save(createCommentRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GenericResponse.SuccessResponse("Comment created"));
    }
}
