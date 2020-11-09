package com.heythere.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class UpdateUserProfileRequestDto {
    @NotNull
    private final Long requestUserId;

    @NotNull
    @Email
    private final String email;

    @NotNull
    private final String name;

    @NotNull
    private final String password;
}
