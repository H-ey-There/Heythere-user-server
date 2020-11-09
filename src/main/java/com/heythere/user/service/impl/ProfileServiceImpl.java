package com.heythere.user.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heythere.user.dto.RegisterUserRequestUserDto;
import com.heythere.user.dto.UpdateUserProfileRequestDto;
import com.heythere.user.message.domain.MailEventDto;
import com.heythere.user.message.domain.UserEventDto;
import com.heythere.user.message.domain.UserMessageDto;
import com.heythere.user.exception.ResourceNotFoundException;
import com.heythere.user.mapper.UserResponseMapper;
import com.heythere.user.message.sender.UserEventProducer;
import com.heythere.user.model.User;
import com.heythere.user.repository.UserRepository;
import com.heythere.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final UserEventProducer userEventProducer;
    private final AmazonS3StorageService amazonS3StorageService;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public Long save(final RegisterUserRequestUserDto payload) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {
        final User user = userRepository.save(User.builder()
                .email(payload.getEmail())
                .name(payload.getName())
                .password(passwordEncoder.encode(payload.getPassword()))
                .build());

        userEventProducer.sendWelcomeMailEvent(mailEventDtoBuilder(user));
        userEventProducer.sendUserUpdateEvent(userEventDtoBuilder(user));

        return user.getId();
    }

    @Override
    @Transactional
    public UserResponseMapper findUserById(final Long requestUserId) {
        final User user = userRepository.findById(requestUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",requestUserId));

        return UserResponseMapper.of(user);
    }

    @Override
    @Transactional
    public UserResponseMapper updateImg(final Long requestUserId, final MultipartFile image) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        final User user = userRepository.findById(requestUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",requestUserId));

        user.updateImg(amazonS3StorageService.upload(image, user, "profile"));

        userEventProducer.sendUserUpdateEvent(userEventDtoBuilder(user));
        return UserResponseMapper.of(user);
    }

    @Override
    @Transactional
    public UserResponseMapper updateProfile(final UpdateUserProfileRequestDto payload) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {
        final User user = userRepository.findById(payload.getRequestUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User","id",payload.getRequestUserId()))
                .update(payload);

        userEventProducer.sendUserUpdateEvent(userEventDtoBuilder(user));
        return UserResponseMapper.of(user);
    }

    @Override
    @Transactional
    public void deleteUserById(final Long requestUserId) throws JsonProcessingException {
        final User user = userRepository.findById(requestUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User","id", requestUserId));

        userEventProducer.sendUserDeletedEvent(userEventDtoBuilder(user));
        userRepository.delete(user);
    }

    private UserEventDto userEventDtoBuilder(final User user) {
        final UserMessageDto userMessage = UserMessageDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .img(user.getImg())
                .build();

        return UserEventDto.builder()
                .userEventId(user.getId().intValue())
                .userMessageDto(userMessage)
                .build();
    }

    private MailEventDto mailEventDtoBuilder(final User user) {
        return MailEventDto.builder()
                .userId(user.getId().intValue())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
