package com.heythere.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterRequestUserDto {
    private final String email;
    private final String name;
    private final String img;
}
