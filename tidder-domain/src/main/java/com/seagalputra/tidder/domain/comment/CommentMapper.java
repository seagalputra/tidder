package com.seagalputra.tidder.domain.comment;

import com.seagalputra.tidder.api.comment.request.CreateCommentRequest;
import com.seagalputra.tidder.api.comment.response.CommentResponse;
import com.seagalputra.tidder.domain.comment.entity.Comment;
import com.seagalputra.tidder.domain.post.entity.Post;
import com.seagalputra.tidder.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "createCommentRequest.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Comment map(CreateCommentRequest createCommentRequest, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentResponse mapToResponse(Comment comment);
}
