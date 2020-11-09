package com.heythere.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heythere.user.dto.RegisterUserRequestUserDto;
import com.heythere.user.dto.UpdateUserProfileRequestDto;
import com.heythere.user.mapper.UserResponseMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface ProfileService {
    Long save(final RegisterUserRequestUserDto payload) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException;
    UserResponseMapper findUserById(final Long requestUserId);
    UserResponseMapper updateImg(final Long requestUserId, final MultipartFile image) throws IOException, InterruptedException, ExecutionException, TimeoutException;
    UserResponseMapper updateProfile(final UpdateUserProfileRequestDto payload) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException;
    void deleteUserById(final Long requestUserId) throws JsonProcessingException;
}
