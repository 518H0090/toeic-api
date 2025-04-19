package com.toeic.auth.application.mapper;

import com.toeic.auth.application.dto.AddUserDto;
import com.toeic.auth.domain.model.Users;

public class UserMapper {
    public static Users mapToTestSessionDto(AddUserDto userDto) {

        if (userDto == null)
            throw new NullPointerException("userDto is null");

        return Users.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .role(userDto.getRole())
                .build();
    }
}
