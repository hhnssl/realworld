package springboot.java17.realworld.service;

import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.java17.realworld.api.dto.userDtos.request.LoginUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.request.NewUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.request.UpdateUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.response.UserResponseDto;
import springboot.java17.realworld.api.exception.DuplicatedEmailException;
import springboot.java17.realworld.api.exception.DuplicatedUsernameException;
import springboot.java17.realworld.api.exception.UserNotAuthenticatedException;
import springboot.java17.realworld.config.jwt.TokenProvider;
import springboot.java17.realworld.entity.UserEntity;
import springboot.java17.realworld.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    @Transactional
    public UserResponseDto register(NewUserRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicatedEmailException("이미 사용 중인 이메일입니다.");
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicatedUsernameException("이미 사용 중인 사용자 이름입니다.");
        }

        UserEntity user = UserEntity.builder()
            .username(dto.getUsername())
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .role("ROLE_USER")
            .build();
        userRepository.save(user);

        String token = tokenProvider.generateToken(user, Duration.ofHours(2));
        
        return UserResponseDto.fromEntity(user, token);
    }

    @Override
    @Transactional
    public UserResponseDto login(LoginUserRequestDto dto) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);

            UserEntity user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
            String token = tokenProvider.generateToken(user, Duration.ofHours(2));

            return UserResponseDto.fromEntity(user, token);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("이메일 또는 비밀번호가 맞지 않습니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getCurrentUser() {
        UserEntity currentUser = getCurrentUserEntity()
            .orElseThrow(() -> new UserNotAuthenticatedException("인증 정보가 없습니다."));

        // TODO: 만약 헤더에서 토큰을 추출할 수 있다면 그 토큰을 그대로 전달하기
        return UserResponseDto.fromEntity(currentUser, null);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UpdateUserRequestDto dto) {
        UserEntity currentUser = getCurrentUserEntity()
            .orElseThrow(() -> new UserNotAuthenticatedException("인증 정보가 없습니다."));

        // 비밀번호가 null이 아닐 경우에만 인코딩하여 업데이트
        String encodedPassword = Optional.ofNullable(dto.getPassword())
            .filter(p -> !p.isBlank()) // 비어있지 않은 경우에만
            .map(passwordEncoder::encode)
            .orElse(currentUser.getPassword()); // 아니면 기존 비밀번호 유지

        currentUser.update(dto.getUsername(), encodedPassword, dto.getImage(), dto.getBio());

        String newToken = tokenProvider.generateToken(currentUser, Duration.ofHours(2));

        return UserResponseDto.fromEntity(currentUser, newToken);
    }


    private Optional<UserEntity> getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(
            authentication.getPrincipal())) {
            return Optional.empty();
        }
        String userEmail = authentication.getName();
        return userRepository.findByEmail(userEmail);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public UserEntity findById(Long userId) {
//        return userRepository.findById(userId)
//            .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 사용자를 찾을 수 없습니다: " + userId));
//    }
}