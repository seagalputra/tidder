package com.seagalputra.tidder.domain.subreddit;

import com.seagalputra.tidder.api.subreddit.SubredditService;
import com.seagalputra.tidder.api.subreddit.request.CreateSubredditRequest;
import com.seagalputra.tidder.api.subreddit.response.SubredditResponse;
import com.seagalputra.tidder.domain.subreddit.entity.Subreddit;
import com.seagalputra.tidder.domain.subreddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditServiceImpl implements SubredditService {

    private final SubredditRepository subredditRepository;

    @Override
    @Transactional
    public SubredditResponse save(CreateSubredditRequest createSubredditRequest) {
        Subreddit subreddit = mapToSubreddit(createSubredditRequest);
        Subreddit savedSubreddit = subredditRepository.save(subreddit);
        return SubredditResponse.builder()
                .id(savedSubreddit.getId())
                .name(savedSubreddit.getName())
                .description(savedSubreddit.getDescription())
                .numberOfPosts(createSubredditRequest.getNumberOfPosts())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubredditResponse> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapToSubredditResponse)
                .collect(Collectors.toList());
    }

    private SubredditResponse mapToSubredditResponse(Subreddit subreddit) {
        return SubredditResponse.builder()
                .id(subreddit.getId())
                .name(subreddit.getName())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }

    private Subreddit mapToSubreddit(CreateSubredditRequest createSubredditRequest) {
        return Subreddit.builder().name(createSubredditRequest.getName())
                .description(createSubredditRequest.getDescription())
                .build();
    }
}
