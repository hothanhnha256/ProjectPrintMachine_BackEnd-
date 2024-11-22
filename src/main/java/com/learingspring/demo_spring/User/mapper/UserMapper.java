package com.learingspring.demo_spring.User.mapper;

import com.learingspring.demo_spring.User.dto.request.ProfileCreationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.learingspring.demo_spring.User.dto.request.UserCreationRequest;
import com.learingspring.demo_spring.User.dto.request.UserUpdateRequest;
import com.learingspring.demo_spring.User.dto.response.UserResponse;
import com.learingspring.demo_spring.User.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {


    User toUser(UserCreationRequest user);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    ProfileCreationRequest toProfileCreationRequest(User user);

}
