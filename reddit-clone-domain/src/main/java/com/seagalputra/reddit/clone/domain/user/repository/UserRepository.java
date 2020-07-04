package com.seagalputra.reddit.clone.domain.user.repository;

import com.seagalputra.reddit.clone.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
