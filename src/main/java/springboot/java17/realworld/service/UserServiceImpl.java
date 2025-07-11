package springboot.java17.realworld.service;


import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.java17.realworld.api.dto.userDtos.request.LoginUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.request.NewUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.response.UserResponseDto;
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
    public UserResponseDto save(NewUserRequestDto dto) {
        // 1. DTO를 바탕으로 UserEntity 생성 및 비밀번호 암호화
        UserEntity user = UserEntity.builder()
            .username(dto.getUsername())
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .role("ROLE_USER")
            .build();

        // 2. DB에 사용자 저장
        userRepository.save(user);

        // 3. 회원가입 직후 바로 토큰을 발급
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
        // 1. 사용자가 제출한 이메일과 비밀번호로 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        // 2. AuthenticationManager에 인증을 위임
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 3. 인증된 Principal을 CustomUserDetails로 형 변환
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 4. CustomUserDetails에서 실제 UserEntity를 가져옴
        UserEntity user = userDetails.getUser();

        // 5. 토큰 생성
        String token = tokenProvider.generateToken(user, Duration.ofHours(2));

        return UserResponseDto.fromEntity(user, token);
    }

    public UserResponseDto findUser() {
        UserDetails user;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            user = (UserDetails) authentication.getPrincipal();

            UserEntity userEntity = userRepository.findByEmail(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException(""));
            return UserResponseDto.fromEntity(userEntity, null);
        }

        return null;
    }
}
