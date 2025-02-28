package org.example.authservice.service;

import org.example.authservice.dto.user.UserRequestDto;
import org.example.authservice.dto.user.UserResponseDto;
import org.example.authservice.model.User;

public interface UserService {
    UserResponseDto saveUser(UserRequestDto userRequestDto);

    User getUserByEmail(String email);

    User getCurrentUser();

}
