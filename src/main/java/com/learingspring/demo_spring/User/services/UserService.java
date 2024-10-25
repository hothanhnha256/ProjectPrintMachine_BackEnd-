package com.learingspring.demo_spring.User.services;

import java.util.List;

import com.learingspring.demo_spring.enums.Roles;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learingspring.demo_spring.User.dto.request.UserCreationRequest;
import com.learingspring.demo_spring.User.dto.request.UserUpdateRequest;
import com.learingspring.demo_spring.User.dto.response.UserResponse;
import com.learingspring.demo_spring.User.entity.User;
import com.learingspring.demo_spring.exception.AppException;
import com.learingspring.demo_spring.exception.ErrorCode;
import com.learingspring.demo_spring.User.mapper.UserMapper;
import com.learingspring.demo_spring.User.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public UserResponse createRequest(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Roles.USER);

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        log.info("User created: {}", user);
        return userMapper.toUserResponse(user);
    }

    @PostAuthorize("returnObject.username==authentication.name")
    public UserResponse getUser(String id) {

        log.info("inside getUser method");

        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_INVALID));

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')") // phân quyền theo ROLE
    public List<UserResponse> getAllUsers() {

        log.info("inside getAllUsers");

        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse updateUser(String userID, UserUpdateRequest user) {
        User userToUpdate = userRepository.findById(userID).orElseThrow(() -> new AppException(ErrorCode.USER_INVALID));
        userMapper.updateUser(userToUpdate, user);
        userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.toUserResponse(userRepository.save(userToUpdate));
    }

    public UserResponse deleteUser(String id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_INVALID));
        return userMapper.toUserResponse(userToDelete);
    }

    @PostAuthorize("returnObject.username==authentication.name") // Recheck to auth
    public UserResponse getMyInfo() {

        var context = SecurityContextHolder.getContext();

        String name = context.getAuthentication().getName();
        User finduser = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        return userMapper.toUserResponse(finduser);
    }
}
