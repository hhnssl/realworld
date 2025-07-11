package springboot.java17.realworld.repository;

import java.util.Optional;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.java17.realworld.entity.FollowEntity;
import springboot.java17.realworld.entity.UserEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    FollowEntity findFollowingByUser(UserEntity user);
}
