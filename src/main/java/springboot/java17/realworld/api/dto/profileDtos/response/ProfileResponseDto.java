package springboot.java17.realworld.api.dto.profileDtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springboot.java17.realworld.entity.UserEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {

    private ProfileDto profile;

    public static ProfileResponseDto fromEntity(UserEntity entity, boolean isFollowing) {
        return ProfileResponseDto.builder()
            .profile(ProfileDto.fromEntity(entity, isFollowing))
            .build();
    }
}
