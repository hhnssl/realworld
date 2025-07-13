package springboot.java17.realworld.service;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springboot.java17.realworld.entity.UserEntity;

@Getter
public class CustomUserDetails implements UserDetails {

    private final UserEntity user;

    public CustomUserDetails(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 만료되지 않음
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠금되지 않았음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 만료되지 않음
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}