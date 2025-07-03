package springboot.java17.realworld.api.dto.userDtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import springboot.java17.realworld.entity.UserEntity;


@Getter
@Builder
public class UserResponseDto {
    private UserDto user;

    public static UserResponseDto fromEntity(UserEntity user){
        return UserResponseDto.builder()
            .user(UserDto.fromEntity(user))
            .build();
    }
}
