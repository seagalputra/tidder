package com.seagalputra.tidder.domain.comment;

import com.seagalputra.tidder.api.comment.CommentService;
import com.seagalputra.tidder.api.comment.request.CreateCommentRequest;
import com.seagalputra.tidder.api.email.EmailService;
import com.seagalputra.tidder.api.email.request.SendEmailRequest;
import com.seagalputra.tidder.api.exception.PostNotFoundException;
import com.seagalputra.tidder.api.exception.SpringTidderException;
import com.seagalputra.tidder.api.user.AuthService;
import com.seagalputra.tidder.common.MailContentBuilder;
import com.seagalputra.tidder.domain.comment.entity.Comment;
import com.seagalputra.tidder.domain.comment.repository.CommentRepository;
import com.seagalputra.tidder.domain.post.entity.Post;
import com.seagalputra.tidder.domain.post.repository.PostRepository;
import com.seagalputra.tidder.domain.user.entity.User;
import com.seagalputra.tidder.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final EmailService emailService;

    @Override
    public void save(CreateCommentRequest createCommentRequest) {
        Post post = postRepository.findById(createCommentRequest.getPostId())
                .orElseThrow(() -> new PostNotFoundException(createCommentRequest.getPostId().toString()));
        User user = userRepository.findById(authService.getCurrentUser().getUserId())
                .orElseThrow(() -> new SpringTidderException("User not found"));
        Comment comment = commentMapper.map(createCommentRequest, post, user);
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        emailService.sendEmail(SendEmailRequest.builder()
                .subject(user.getUsername() + "Commented on your post")
                .recipient(user.getEmail())
                .body(message)
                .build());
    }
}
