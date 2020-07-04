package com.seagalputra.tidder.web.controller;

import com.seagalputra.tidder.api.subreddit.SubredditService;
import com.seagalputra.tidder.api.subreddit.request.CreateSubredditRequest;
import com.seagalputra.tidder.api.subreddit.response.SubredditResponse;
import com.seagalputra.tidder.api.web.GenericResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<GenericResponse> createSubreddit(@RequestBody CreateSubredditRequest request) {
        SubredditResponse response = subredditService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GenericResponse.SuccessResponse(response));
    }

    @GetMapping
    public ResponseEntity<GenericResponse> getAllSubreddits() {
        List<SubredditResponse> response = subredditService.getAll();

        return ResponseEntity.status(HttpStatus.OK)
                .body(GenericResponse.SuccessResponse(response));
    }
}
