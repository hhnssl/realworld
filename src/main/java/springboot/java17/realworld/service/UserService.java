package springboot.java17.realworld.service;

import springboot.java17.realworld.api.dto.userDtos.request.LoginUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.request.NewUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.request.UpdateUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.response.UserResponseDto;
import springboot.java17.realworld.entity.UserEntity;

public interface UserService {

    UserResponseDto register(NewUserRequestDto dto);

    UserResponseDto login(LoginUserRequestDto dto);

    UserResponseDto getCurrentUser();

    UserResponseDto updateUser(UpdateUserRequestDto dto);
}
