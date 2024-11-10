package com.learingspring.demo_spring.User.controller;


import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.learingspring.demo_spring.User.dto.request.UserCreationRequest;
import com.learingspring.demo_spring.User.dto.request.UserUpdateRequest;
import com.learingspring.demo_spring.exception.ApiResponse;
import com.learingspring.demo_spring.User.dto.response.UserResponse;
import com.learingspring.demo_spring.User.services.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest user) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(userService.createRequest(user));
        return apiResponse;
    }

    @PostMapping("/createAdmin")
    ApiResponse<UserResponse> createAdmin(@RequestBody @Valid UserCreationRequest user) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(userService.createAdmin(user));
        return apiResponse;
    }
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {

//      var authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("Username: {}", authentication.getName());
//        authentication.getAuthorities().forEach(grantedAuthority -> log.info("GrantedAuthority: {}", grantedAuthority.getAuthority()));

        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(userService.getUser(userId));

        return apiResponse;
    }

    @GetMapping()
    ApiResponse<Page<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)  {

        log.info("getAllUsers");

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        authentication
                .getAuthorities()
                .forEach(grantedAuthority -> log.info("GrantedAuthority: {}", grantedAuthority.getAuthority()));

        ApiResponse<Page<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(userService.getAllUsers(page,size));
        return apiResponse;
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest user) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(userService.updateUser(userId, user));
        return apiResponse;
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable("userId") String userId) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(userService.deleteUser(userId));
        return apiResponse;
    }
}
