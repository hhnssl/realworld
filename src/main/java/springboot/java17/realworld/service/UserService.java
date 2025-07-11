package springboot.java17.realworld.service;

import springboot.java17.realworld.api.dto.userDtos.request.LoginUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.request.NewUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.request.UpdateUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.response.UserResponseDto;
import springboot.java17.realworld.entity.UserEntity;

public interface UserService {

    UserResponseDto save(NewUserRequestDto dto);

    UserEntity findById(Long userId);

    UserResponseDto login(LoginUserRequestDto dto);

    UserResponseDto findUser();

    UserResponseDto updateUser(UpdateUserRequestDto dto);
}
