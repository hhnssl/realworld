package springboot.java17.realworld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.java17.realworld.api.dto.profileDtos.response.ProfileResponseDto;
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
    @Transactional(readOnly = true)
    public ProfileResponseDto getProfileByUsername(String username) {
        UserEntity profileOwner = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다: " + username));

        boolean isFollowing = false;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 토큰이 있을 경우
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            UserEntity currentUser = userDetails.getUser();

            isFollowing = followRepository.existsByUserAndFollowing(currentUser, profileOwner);
        }

        return ProfileResponseDto.fromEntity(profileOwner, isFollowing);
    }

    @Override
    @Transactional
    public ProfileResponseDto followProfileByUsername(String currentUserEmail,
        String usernameToFollow) {
        UserEntity currentUser = findUserByEmail(currentUserEmail);
        UserEntity userToFollow = findUserByUsername(usernameToFollow);

        // 셀프 팔로우 금지
        if (currentUser.getId().equals(userToFollow.getId())) {
            throw new IllegalArgumentException("You cannot follow yourself.");
        }

        // 이미 팔로우하고 있는지 확인
        if (followRepository.existsByUserAndFollowing(currentUser, userToFollow)) {
            // 이미 팔로우 중이라면, 추가 작업 없이 현재 프로필 상태를 그대로 반환
            return ProfileResponseDto.fromEntity(userToFollow, true);
        }

        // Follow 관계 생성 및 저장
        FollowEntity follow = FollowEntity.builder()
            .user(currentUser)
            .following(userToFollow)
            .build();
        followRepository.save(follow);

        return ProfileResponseDto.fromEntity(userToFollow, true);
    }

    @Transactional
    @Override
    public ProfileResponseDto unfollowProfileByUsername(String currentUserEmail, String usernameToUnfollow) {
        UserEntity currentUser = findUserByEmail(currentUserEmail);
        UserEntity userToFollow = findUserByUsername(usernameToUnfollow);

        // Follow 관계 삭제
        followRepository.deleteByUserAndFollowing(currentUser, userToFollow);

        return ProfileResponseDto.fromEntity(userToFollow, false);
    }

    // 유틸리티 메서드로 분리하여 코드 가독성 향상
    private UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(
                () -> new UsernameNotFoundException("Follower not found with email: " + email));
    }

    private UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                "User to follow not found with username: " + username));
    }
}
