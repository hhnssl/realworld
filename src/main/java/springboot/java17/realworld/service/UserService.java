package springboot.java17.realworld.service;

import springboot.java17.realworld.api.dto.userDtos.request.NewUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.response.UserResponseDto;

public interface UserService {

    UserResponseDto save(NewUserRequestDto dto);
}
