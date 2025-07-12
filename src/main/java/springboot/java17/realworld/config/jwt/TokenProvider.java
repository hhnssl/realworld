package springboot.java17.realworld.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.security.Key;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import springboot.java17.realworld.entity.UserEntity;
import springboot.java17.realworld.service.CustomUserDetails;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(UserEntity user, Duration expiredAt) {
        Date now = new Date();

        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // JWT 토큰 생성 메서드
    private String makeToken(Date expiry, UserEntity user) {
        Date now = new Date();

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuer(jwtProperties.getIssuer())
            .setIssuedAt(now)
            .setExpiration(expiry)
            .setSubject(user.getEmail())
            .claim("id", user.getId())
            .claim("auth", user.getRole())
            .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
            .compact();
    }

    // JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.warn("Invalid JWT signature", e);
            return false;
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token", e);
            return false;
        } catch (UnsupportedJwtException e) {
            log.warn("Unsupported JWT token", e);
            return false;
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty.", e);
            return false;
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String role = claims.get("auth", String.class);

        Set<SimpleGrantedAuthority> authorities = Collections.singleton(
            new SimpleGrantedAuthority(role));

        UserEntity userEntity = UserEntity.builder()
            .id(claims.get("id", Long.class))
            .email(claims.getSubject()) // 'sub' 클레임은 이메일
            .role(role)
            .build();

        CustomUserDetails userDetails = new CustomUserDetails(userEntity);

        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

    // 토큰 기반으로 유저 아이디를 가져오는 메서드
    public Long getUserId(String token) {
        return getClaims(token).get("id", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
            .setSigningKey(jwtProperties.getSecretKey())
            .parseClaimsJws(token)
            .getBody();
    }

}
