package com.seagalputra.tidder.domain.post.repository;

import com.seagalputra.tidder.domain.post.entity.Post;
import com.seagalputra.tidder.domain.subreddit.entity.Subreddit;
import com.seagalputra.tidder.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);
}
