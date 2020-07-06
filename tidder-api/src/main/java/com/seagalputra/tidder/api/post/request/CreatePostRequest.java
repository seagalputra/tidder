package com.seagalputra.tidder.api.post.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {
    private String subredditName;
    private String postName;
    private String url;
    private String description;
}
