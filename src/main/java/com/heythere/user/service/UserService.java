package com.heythere.user.service;

import com.heythere.user.dto.SubscribeRequestDto;
import com.heythere.user.mapper.SubscribeProcessResponseMapper;
import com.heythere.user.mapper.UserResponseMapper;

import java.util.List;

public interface UserService {
    List<UserResponseMapper> findAllFollowingUsers(final Long requestUserId);
    List<UserResponseMapper> findAllMySubscribers(final Long requestUserId);
    SubscribeProcessResponseMapper subscribeStatus( final SubscribeRequestDto payload);
    SubscribeProcessResponseMapper subscribe(final SubscribeRequestDto payload);
    SubscribeProcessResponseMapper cancelSubscribe(final SubscribeRequestDto payload);
}
