package org.example.authservice.mapper;

import org.example.authservice.dto.user.UserRequestDto;
import org.example.authservice.dto.user.UserResponseDto;
import org.example.authservice.model.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        builder = @Builder(disableBuilder = true))
public interface UserMapper {

    User convertToUser(UserRequestDto userRequestDto);

    UserResponseDto convertToUserResponse(User user);
}
