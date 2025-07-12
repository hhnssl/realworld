package springboot.java17.realworld.service;


import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.java17.realworld.api.dto.userDtos.request.LoginUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.request.NewUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.request.UpdateUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.response.UserResponseDto;
import springboot.java17.realworld.config.jwt.TokenProvider;
import springboot.java17.realworld.entity.UserEntity;
import springboot.java17.realworld.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
        UserEntity user = UserEntity.builder()
            .username(dto.getUsername())
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .role("ROLE_USER") //TODO: enum
            .build();

        userRepository.save(user);

        // 회원가입 직후 바로 토큰 발급
        String token = tokenProvider.generateToken(user, Duration.ofHours(2));

        return UserResponseDto.fromEntity(user, token);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Unexpected user with id: " + userId));
    }

    @Override
    @Transactional
    public UserResponseDto login(LoginUserRequestDto dto) {
        try {
            // 1. 인증 시도
            UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);

            // 2. 인증 성공시 JWT 생성
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            UserEntity user = userDetails.getUser();
            String token = tokenProvider.generateToken(user, Duration.ofHours(2));

            return UserResponseDto.fromEntity(user, token);
        } catch (AuthenticationException e) {
            // 3. 인증 실패 시 예외 발생
            throw new BadCredentialsException("이메일 또는 비밀번호가 맞지 않습니다.");
        }
    }

    public UserResponseDto getCurrentUser() {
        UserEntity currentUser = findCurrentUser();

        return UserResponseDto.fromEntity(currentUser, null); // TODO: 토큰 발급할 필요 있는지 확인할 것

    }

    public UserResponseDto updateUser(UpdateUserRequestDto dto) {
        UserDetails userDetails;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            userDetails = (UserDetails) authentication.getPrincipal();

            UserEntity user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException(""));

            user.setUsername(dto.getUsername() == null ? user.getUsername() : dto.getUsername());
            user.setPassword(dto.getPassword() == null ? user.getPassword()
                : passwordEncoder.encode(dto.getPassword()));
            user.setImage(dto.getImage() == null ? user.getImage() : dto.getImage());
            user.setBio(dto.getBio() == null ? user.getBio() : dto.getBio());

            userRepository.save(user);

            return UserResponseDto.fromEntity(user, null);
        }

        return null;
    }

    private UserEntity findCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new BadCredentialsException("인증 정보가 없습니다.");
        }
        return userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new UsernameNotFoundException(
                "User not found with email: " + authentication.getName()));
    }
}
