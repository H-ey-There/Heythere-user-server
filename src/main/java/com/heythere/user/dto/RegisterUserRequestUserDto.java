package com.heythere.user.dto;

import com.heythere.user.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class RegisterUserRequestUserDto {
    @NotNull
    @Email
    private final String email;

    @NotNull
    private final String name;

    @NotNull
    private final String password;

    public User toUserEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}
