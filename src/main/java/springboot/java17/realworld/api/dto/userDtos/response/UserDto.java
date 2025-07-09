package springboot.java17.realworld.api.dto.userDtos.response;

import lombok.Builder;
import lombok.Getter;
import springboot.java17.realworld.entity.UserEntity;

@Getter
@Builder
public class UserDto {
    private String email;

    private String token;

    private String username;

    private String bio;

    private String image;

    public static UserDto fromEntity(UserEntity user, String token){
        UserDto dto = UserDto.builder()
            .email(user.getEmail())
            .token(token)
            .username(user.getUsername())
            .bio(user.getBio())
            .image(user.getImage())
            .build();

        return dto;
    }
}
