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

    private UserEntity user;

    public CustomUserDetails(UserEntity user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // 사용자의 id를 반환(고유한 값)
    @Override
    public String getUsername(){
        return user.getEmail();
    }

    @Override
    public String getPassword(){
        return user.getPassword();
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; // 만료되지 않았음
    }

    // 계정 잠금 여부 반환

    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 만료되지 않았음
    }

    //계정 사용 가능 여부 반환

    @Override
    public boolean isEnabled() {
        return true; // 사용 가능
    }
}
