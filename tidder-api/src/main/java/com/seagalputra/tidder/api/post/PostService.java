package com.seagalputra.tidder.api.post;

import com.seagalputra.tidder.api.post.request.CreatePostRequest;
import com.seagalputra.tidder.api.post.response.PostResponse;

import java.util.List;

public interface PostService {
    void save(CreatePostRequest createPostRequest);
    List<PostResponse> getAllPosts();
    PostResponse getPost(Long id);
    List<PostResponse> getPostBySubreddit(Long id);
    List<PostResponse> getPostByUsername(String username);
}
