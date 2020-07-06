package com.seagalputra.tidder.domain.post;

import com.seagalputra.tidder.api.exception.PostNotFoundException;
import com.seagalputra.tidder.api.exception.SubredditNotFoundException;
import com.seagalputra.tidder.api.post.PostService;
import com.seagalputra.tidder.api.post.request.CreatePostRequest;
import com.seagalputra.tidder.api.post.response.PostResponse;
import com.seagalputra.tidder.api.user.AuthService;
import com.seagalputra.tidder.api.user.response.UserResponse;
import com.seagalputra.tidder.domain.post.entity.Post;
import com.seagalputra.tidder.domain.post.repository.PostRepository;
import com.seagalputra.tidder.domain.subreddit.entity.Subreddit;
import com.seagalputra.tidder.domain.subreddit.repository.SubredditRepository;
import com.seagalputra.tidder.domain.user.entity.User;
import com.seagalputra.tidder.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    @Override
    public void save(CreatePostRequest createPostRequest) {
        Subreddit subreddit = subredditRepository.findByName(createPostRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(createPostRequest.getSubredditName()));
        UserResponse userResponse = authService.getCurrentUser();
        User user = User.builder()
                .userId(userResponse.getUserId())
                .username(userResponse.getUsername())
                .email(userResponse.getEmail())
                .created(userResponse.getCreated())
                .enabled(userResponse.isEnabled())
                .build();
        Post post = postMapper.map(createPostRequest, subreddit, user);
        postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToPostResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToPostResponse(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getPostBySubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException(id.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream()
                .map(postMapper::mapToPostResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getPostByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToPostResponse)
                .collect(Collectors.toList());
    }
}
