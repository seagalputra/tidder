package com.seagalputra.tidder.domain.subreddit;

import com.seagalputra.tidder.api.subreddit.request.CreateSubredditRequest;
import com.seagalputra.tidder.api.subreddit.response.SubredditResponse;
import com.seagalputra.tidder.domain.post.entity.Post;
import com.seagalputra.tidder.domain.subreddit.entity.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditResponse mapToSubredditResponse(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapToSubreddit(CreateSubredditRequest createSubredditRequest);
}
