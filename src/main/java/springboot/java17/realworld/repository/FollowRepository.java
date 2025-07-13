package springboot.java17.realworld.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.java17.realworld.entity.FollowEntity;
import springboot.java17.realworld.entity.UserEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    // 개선점 1: 엔티티 필드명 변경에 따라 메서드명 수정 (user -> follower)
    boolean existsByFollowerAndFollowing(UserEntity follower, UserEntity following);

    void deleteByFollowerAndFollowing(UserEntity follower, UserEntity following);

    // 개선점 2: 피드 기능 구현을 위해 특정 사용자가 팔로우하는 모든 관계를 찾는 메서드 추가
    List<FollowEntity> findAllByFollower(UserEntity follower);
}
