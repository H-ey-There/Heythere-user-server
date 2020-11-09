package com.heythere.user.service.impl;

import com.heythere.user.dto.SubscribeRequestDto;
import com.heythere.user.exception.ResourceNotFoundException;
import com.heythere.user.mapper.SubscribeProcessResponseMapper;
import com.heythere.user.mapper.UserResponseMapper;
import com.heythere.user.message.sender.UserEventProducer;
import com.heythere.user.model.Follower;
import com.heythere.user.model.Subscriber;
import com.heythere.user.model.User;
import com.heythere.user.repository.FollowerRepository;
import com.heythere.user.repository.SubscriberRepository;
import com.heythere.user.repository.UserRepository;
import com.heythere.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubscriberRepository subscriberRepository;
    private final FollowerRepository followerRepository;
    private final UserEventProducer userEventProducer;


    @Override
    @Transactional
    public List<UserResponseMapper> findAllFollowingUsers(Long requestUserId) {
        return getUser(requestUserId)
                .getFollowers()
                .stream()
                .map(UserResponseMapper::ofFollower)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<UserResponseMapper> findAllMySubscribers(Long requestUserId) {
        return getUser(requestUserId)
                .getSubscribers()
                .stream()
                .map(UserResponseMapper::ofSubscriber)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SubscribeProcessResponseMapper subscribeStatus(SubscribeRequestDto payload) {
        final User requestUser = getUser(payload.getRequestUserId());
        final User targetUser = getUser(payload.getTargetUserId());

        return requestUser.getFollowers().indexOf(getFollower(targetUser))!=-1
            ?  new SubscribeProcessResponseMapper(true)
            :  new SubscribeProcessResponseMapper(false);
    }

    @Override
    @Transactional
    public SubscribeProcessResponseMapper subscribe(SubscribeRequestDto payload) {
        final User requestUser = getUser(payload.getRequestUserId());
        final User targetUser = getUser(payload.getTargetUserId());

        requestUser.addFollower(followerRepository.save(getFollower(targetUser)));
        targetUser.addSubscriber(subscriberRepository.save(getSubscriber(requestUser)));
        return new SubscribeProcessResponseMapper(true);
    }

    @Override
    @Transactional
    public SubscribeProcessResponseMapper cancelSubscribe(SubscribeRequestDto payload) {
        final User requestUser = getUser(payload.getRequestUserId());
        final User targetUser = getUser(payload.getTargetUserId());

        final Follower follower = followerRepository.findById(targetUser.getId()).get();
        requestUser.getFollowers().remove(follower);
        followerRepository.delete(follower);

        final Subscriber subscriber = subscriberRepository.findById(requestUser.getId()).get();
        targetUser.getSubscribers().remove(subscriber);
        subscriberRepository.delete(subscriber);

        return new SubscribeProcessResponseMapper(false);
    }


    private Follower getFollower(final User user) {
        return Follower.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .img(user.getImg())
                .build();
    }

    private Subscriber getSubscriber(final User user) {
        return Subscriber.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .img(user.getImg())
                .build();
    }

    private User getUser(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",id));
    }

}
