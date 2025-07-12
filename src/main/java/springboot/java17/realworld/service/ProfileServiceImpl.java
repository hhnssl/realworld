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

    @Transactional
    @Override
    public ProfileResponseDto unfollowProfileByUsername(String username) {
        UserEntity followingUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 username 입니다."));

        UserDetails userDetails;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            userDetails = (UserDetails) authentication.getPrincipal();

            UserEntity user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException(""));

            followRepository.deleteFollowEntityByFollowingAndUser(followingUser, user);

            return ProfileResponseDto.fromEntity(followingUser, false);
        }

        return null;
    }
}
