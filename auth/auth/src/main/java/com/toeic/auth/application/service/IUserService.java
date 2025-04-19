package com.toeic.auth.application.service;

import java.util.List;
import java.util.Optional;

import com.toeic.auth.application.dto.AddUserDto;
import com.toeic.auth.application.dto.UpdateUserDto;
import com.toeic.auth.domain.model.Users;

public interface IUserService {

    Users registerUser(AddUserDto userDto);

    Users updateUser(UpdateUserDto userDto);

    Optional<Users> findByUsername(String username);

    List<Users> findAll();

    void deleteById(int userId);
}

