package springboot.java17.realworld.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.java17.realworld.entity.FollowEntity;
import springboot.java17.realworld.entity.UserEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    boolean existsByFollowerAndFollowing(UserEntity follower, UserEntity following);

    void deleteByFollowerAndFollowing(UserEntity follower, UserEntity following);

    List<FollowEntity> findAllByFollower(UserEntity follower);
}
