package com.seagalputra.tidder.domain.post;

import com.seagalputra.tidder.api.post.request.CreatePostRequest;
import com.seagalputra.tidder.api.post.response.PostResponse;
import com.seagalputra.tidder.domain.post.entity.Post;
import com.seagalputra.tidder.domain.subreddit.entity.Subreddit;
import com.seagalputra.tidder.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "createPostRequest.description")
    Post map(CreatePostRequest createPostRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostResponse mapToPostResponse(Post post);
}
