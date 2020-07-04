package com.seagalputra.reddit.clone.domain.subreddit.repository;

import com.seagalputra.reddit.clone.domain.subreddit.entity.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
}
