package com.heythere.user.repository;

import com.heythere.user.model.Subscriber;
import com.heythere.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    List<Subscriber> findAllByUser(final User user);
}
