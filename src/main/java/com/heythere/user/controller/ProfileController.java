package com.heythere.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heythere.user.dto.RegisterUserRequestUserDto;
import com.heythere.user.dto.UpdateUserProfileRequestDto;
import com.heythere.user.mapper.UserResponseMapper;
import com.heythere.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RequestMapping("v1")
@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping("registration")
    public ResponseEntity<Long> registerUser(@RequestBody @Valid final RegisterUserRequestUserDto payload) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {
        return new ResponseEntity<>(profileService.save(payload), HttpStatus.CREATED);
    }

    @GetMapping("profile/{userId}")
    public UserResponseMapper findUserById(@PathVariable("userId") final Long userId) {
        return profileService.findUserById(userId);
    }

    @PutMapping("profile/update/image")
    public UserResponseMapper updateImg(@RequestParam("userId") final Long userId,
                                        @RequestParam("image") final MultipartFile image) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        return profileService.updateImg(userId, image);
    }

    @PutMapping("profile/update")
    public UserResponseMapper updateProfile(@RequestBody @Valid final UpdateUserProfileRequestDto payload) throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {
        return profileService.updateProfile(payload);
    }

    @DeleteMapping("delete/{userId}")
    public void deleteUserById(@PathVariable("userId") final Long userId) throws JsonProcessingException {
        profileService.deleteUserById(userId);
    }
}
