package com.heythere.user.repository;

import com.heythere.user.model.Follower;
import com.heythere.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> findAllByUser(final User user);
}
