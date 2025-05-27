package springboot.java17.realworld.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.java17.realworld.entity.FollowEntity;
import springboot.java17.realworld.entity.UserEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    Optional<FollowEntity> findByUserAndFollowing(UserEntity user, UserEntity following);

    void deleteById(Long id);
}
