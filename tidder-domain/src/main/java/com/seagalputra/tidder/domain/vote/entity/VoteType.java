package com.seagalputra.tidder.domain.vote.entity;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(1),
    ;

    VoteType(int direction) {
    }
}
