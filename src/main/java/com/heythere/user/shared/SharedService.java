package com.heythere.user.shared;

import com.heythere.user.exception.ResourceNotFoundException;
import com.heythere.user.model.User;
import com.heythere.user.repository.FollowerRepository;
import com.heythere.user.repository.SubscriberRepository;
import com.heythere.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SharedService {
    protected final UserRepository userRepository;
    protected final SubscriberRepository subscriberRepository;
    protected final FollowerRepository followerRepository;

    protected User getCurrentUser(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("User", "id", id));
    }
}
