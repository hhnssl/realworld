package springboot.java17.realworld.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.java17.realworld.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}
