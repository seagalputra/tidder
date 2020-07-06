package com.seagalputra.tidder.api.subreddit;

import com.seagalputra.tidder.api.subreddit.request.CreateSubredditRequest;
import com.seagalputra.tidder.api.subreddit.response.SubredditResponse;

import java.util.List;

public interface SubredditService {
    SubredditResponse save(CreateSubredditRequest createSubredditRequest);
    List<SubredditResponse> getAll();
    SubredditResponse getSubreddit(Long id);
}
