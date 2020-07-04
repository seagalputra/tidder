package com.seagalputra.tidder.api.subreddit.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSubredditRequest {
    private String name;
    private String description;
    private Integer numberOfPosts;
}
