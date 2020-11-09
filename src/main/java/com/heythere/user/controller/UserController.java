package com.heythere.user.controller;

import com.heythere.user.dto.SubscribeRequestDto;
import com.heythere.user.mapper.SubscribeProcessResponseMapper;
import com.heythere.user.mapper.UserResponseMapper;
import com.heythere.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("v1")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;


    @GetMapping("followers")
    public List<UserResponseMapper> findAllFollowingUsers(@RequestParam("userId") final Long userId) {
        return userService.findAllFollowingUsers(userId);
    }

    @GetMapping("subscribers")
    public List<UserResponseMapper> findAllMySubscribers(@RequestParam("userId") final Long userId) {
        return userService.findAllMySubscribers(userId);
    }


    @PostMapping("subscribe/status")
    public SubscribeProcessResponseMapper subscribeStatus(@RequestBody @Valid final SubscribeRequestDto payload) {
        return userService.subscribeStatus(payload);
    }

    @PostMapping("subscribe")
    public SubscribeProcessResponseMapper subscribe(@RequestBody @Valid final SubscribeRequestDto payload) {
        return userService.subscribe(payload);
    }

    @DeleteMapping("subscribe")
    public SubscribeProcessResponseMapper cancelSubscribe(@RequestBody @Valid final SubscribeRequestDto payload) {
        return userService.cancelSubscribe(payload);
    }
 }
