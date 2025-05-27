package springboot.java17.realworld.service;

import org.springframework.stereotype.Service;
import springboot.java17.realworld.api.dto.profileDtos.response.ProfileResponseDto;
import springboot.java17.realworld.entity.UserEntity;
import springboot.java17.realworld.repository.UserRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final FollowService followService;

    /* Authentication 구현 후 삭제*/
    UserEntity followingTestUser = UserEntity.builder()
        .email("followingTestUser@gmail")
        .username("followingTestUser")
        .password("password")
        .build();

    /**/
    public ProfileServiceImpl(UserRepository userRepository, FollowService followService) {
        this.userRepository = userRepository;
        this.followService = followService;
    }

    @Override
    public ProfileResponseDto getProfileByUsername(String username) {

        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 username 입니다."));

        return ProfileResponseDto.fromEntity(userEntity);
    }

    @Override
    public ProfileResponseDto followProfileByUsername(String username) {
        UserEntity followingUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 username 입니다."));

        followService.followUser(followingTestUser, followingUser);

        return ProfileResponseDto.fromEntity(followingUser);
    }

    @Override
    public ProfileResponseDto unfollowProfileByUsername(String username) {
        UserEntity followingUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 username 입니다."));

        followService.unfollowUser(followingTestUser, followingUser);

        return ProfileResponseDto.fromEntity(followingUser);
    }
}
