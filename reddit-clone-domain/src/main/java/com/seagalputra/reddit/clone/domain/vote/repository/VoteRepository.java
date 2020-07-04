package com.seagalputra.reddit.clone.domain.vote.repository;

import com.seagalputra.reddit.clone.domain.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
