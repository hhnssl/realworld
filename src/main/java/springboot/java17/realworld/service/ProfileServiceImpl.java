package springboot.java17.realworld.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.java17.realworld.api.dto.profileDtos.response.ProfileResponseDto;
import springboot.java17.realworld.api.exception.UserNotAuthenticatedException;
import springboot.java17.realworld.entity.FollowEntity;
import springboot.java17.realworld.entity.UserEntity;
import springboot.java17.realworld.repository.FollowRepository;
import springboot.java17.realworld.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public ProfileResponseDto getProfileByUsername(String username) {
        UserEntity profileOwner = findUserByUsername(username);

        boolean isFollowing = getCurrentUser()
            .map(currentUser -> followRepository.existsByFollowerAndFollowing(currentUser,
                profileOwner))
            .orElse(false); // 비로그인 사용자는 항상 false

        return ProfileResponseDto.fromEntity(profileOwner, isFollowing);
    }

    @Override
    @Transactional
    public ProfileResponseDto followProfileByUsername(String usernameToFollow) {
        UserEntity currentUser = getCurrentUser()
            .orElseThrow(() -> new UserNotAuthenticatedException("로그인이 필요한 기능입니다."));
        UserEntity userToFollow = findUserByUsername(usernameToFollow);

        // 셀프 팔로우 금지
        if (currentUser.getId().equals(userToFollow.getId())) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        if (followRepository.existsByFollowerAndFollowing(currentUser, userToFollow)) {
            // 이미 팔로우 중이면 현재 상태 그대로 반환
            return ProfileResponseDto.fromEntity(userToFollow, true);
        }

        FollowEntity follow = FollowEntity.builder()
            .follower(currentUser)
            .following(userToFollow)
            .build();
        followRepository.save(follow);

        return ProfileResponseDto.fromEntity(userToFollow, true);
    }

    @Override
    @Transactional
    public ProfileResponseDto unfollowProfileByUsername(String usernameToUnfollow) {
        UserEntity currentUser = getCurrentUser()
            .orElseThrow(() -> new UserNotAuthenticatedException("로그인이 필요한 기능입니다."));
        UserEntity userToUnfollow = findUserByUsername(usernameToUnfollow);

        followRepository.deleteByFollowerAndFollowing(currentUser, userToUnfollow);

        return ProfileResponseDto.fromEntity(userToUnfollow, false);
    }


    private Optional<UserEntity> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(
            authentication.getPrincipal())) {
            return java.util.Optional.empty();
        }
        String userEmail = authentication.getName();
        return userRepository.findByEmail(userEmail);
    }

    private UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
    }
}