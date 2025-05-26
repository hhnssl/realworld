package springboot.java17.realworld.service;

import org.springframework.stereotype.Service;
import springboot.java17.realworld.api.dto.profileDtos.response.ProfileResponseDto;
import springboot.java17.realworld.entity.UserEntity;
import springboot.java17.realworld.repository.UserRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    public ProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ProfileResponseDto getProfileByUsername(String username) {

        UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 username 입니다."));

        return ProfileResponseDto.fromEntity(userEntity);
    }
}
