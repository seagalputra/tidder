package com.seagalputra.tidder.domain.subreddit;

import com.seagalputra.tidder.api.exception.SpringTidderException;
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
    private final SubredditMapper subredditMapper;

    @Override
    @Transactional
    public SubredditResponse save(CreateSubredditRequest createSubredditRequest) {
        Subreddit subreddit = subredditMapper.mapToSubreddit(createSubredditRequest);
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
                .map(subredditMapper::mapToSubredditResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SubredditResponse getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringTidderException("No subreddit found with id - " + id));
        return subredditMapper.mapToSubredditResponse(subreddit);
    }
}
