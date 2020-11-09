package com.heythere.user.mapper;

import com.heythere.user.model.Follower;
import com.heythere.user.model.Subscriber;
import com.heythere.user.model.User;
import lombok.Builder;
import lombok.Getter;


@Getter
public class UserResponseMapper {
    private final Long id;
    private final String email;
    private final String name;
    private final String img;

    @Builder
    public UserResponseMapper(Long id, String email, String name, String img) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.img = img;
    }

    public static UserResponseMapper of(final User user) {
        return UserResponseMapper.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .img(user.getImg())
                .build();
    }

    public static UserResponseMapper ofFollower(final Follower user) {
        return UserResponseMapper.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .img(user.getImg())
                .build();
    }

    public static UserResponseMapper ofSubscriber(final Subscriber user) {
        return UserResponseMapper.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .img(user.getImg())
                .build();
    }
}
