package springboot.java17.realworld.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@NoArgsConstructor
@AllArgsConstructor

@Getter
@Table(name = "users")
@Entity
public class UserEntity implements UserDetails { // UserDetails를 상속받아서 인증 객체로 사용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String password;

    private String image;

    private String bio;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    // 사용자의 id를 반환(고유한 값)
    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public String getPassword(){
        return password;
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
