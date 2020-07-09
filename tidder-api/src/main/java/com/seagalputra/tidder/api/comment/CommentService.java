package com.seagalputra.tidder.api.comment;

import com.seagalputra.tidder.api.comment.request.CreateCommentRequest;

public interface CommentService {
    void save(CreateCommentRequest createCommentRequest);
}
