package com.heythere.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class SubscribeRequestDto {
    @NotNull
    private final Long requestUserId;

    @NotNull
    private final Long targetUserId;
}
