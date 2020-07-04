package com.seagalputra.reddit.clone.domain.post.repository;

import com.seagalputra.reddit.clone.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
