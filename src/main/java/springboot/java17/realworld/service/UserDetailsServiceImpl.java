package springboot.java17.realworld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.java17.realworld.entity.UserEntity;
import springboot.java17.realworld.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(() ->  new UsernameNotFoundException("User Not Found with email: " + email));

        return new CustomUserDetails(user);
    }
}
