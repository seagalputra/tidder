package com.seagalputra.tidder.api.comment.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {
    private Long postId;
    private Instant createdDate;
    private String text;
    private String username;
}
