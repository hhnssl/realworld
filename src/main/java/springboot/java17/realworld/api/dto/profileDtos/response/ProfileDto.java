package springboot.java17.realworld.api.dto.profileDtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import springboot.java17.realworld.entity.UserEntity;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private String username;

    private String bio;

    private String image;

    private boolean following = false;

    public static ProfileDto fromEntity(UserEntity entity, boolean isFollowing) {
        return ProfileDto.builder()
            .username(entity.getUsername())
            .bio(entity.getBio())
            .image(entity.getImage())
            .following(isFollowing)
            .build();
    }
}
