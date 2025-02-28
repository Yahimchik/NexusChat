package org.example.authservice.service.impl;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.user.UserRequestDto;
import org.example.authservice.dto.user.UserResponseDto;
import org.example.authservice.mapper.UserMapper;
import org.example.authservice.model.User;
import org.example.authservice.model.enums.Role;
import org.example.authservice.model.enums.UserStatus;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        checkEmailDuplicates(userRequestDto);
        User user = createUserFromRequest(userRequestDto);
        user = userRepository.save(user);
        return userMapper.convertToUserResponse(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new EntityExistsException(email));
    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    private void checkEmailDuplicates(UserRequestDto userRequestDto) {
        String email = userRequestDto.getEmail();
        if (isUserExist(email)) {
            throw new EntityExistsException("User with email " + email + " already exists");
        }
    }

    private boolean isUserExist(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    private User createUserFromRequest(UserRequestDto userRequestDto) {
        return User.builder()
                .email(userRequestDto.getEmail())
                .username(userRequestDto.getUsername())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();
    }
}
