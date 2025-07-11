package springboot.java17.realworld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import springboot.java17.realworld.api.dto.profileDtos.response.ProfileResponseDto;
import springboot.java17.realworld.api.dto.userDtos.response.UserResponseDto;
import springboot.java17.realworld.entity.FollowEntity;
import springboot.java17.realworld.entity.UserEntity;
import springboot.java17.realworld.repository.FollowRepository;
import springboot.java17.realworld.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Override
    public ProfileResponseDto getProfileByUsername(String username) {

        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 username 입니다."));

        FollowEntity followState = followRepository.findFollowingByUser(userEntity);

        return ProfileResponseDto.fromEntity(userEntity, followState != null ? true : false);
    }

    @Override
    public ProfileResponseDto followProfileByUsername(String username) {
        UserEntity followingUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 username 입니다."));

        UserDetails userDetails;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            userDetails = (UserDetails) authentication.getPrincipal();

            UserEntity user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException(""));

            // user의 팔로우 목록에 추가하기
            FollowEntity follow = FollowEntity.builder()
                .user(user)
                .following(followingUser)
                .build();

            followRepository.save(follow);

            return ProfileResponseDto.fromEntity(followingUser, true);
        }

        return null;
    }

    @Override
    public ProfileResponseDto unfollowProfileByUsername(String username) {
        UserEntity followingUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 username 입니다."));

        return ProfileResponseDto.fromEntity(followingUser, false);
    }
}
