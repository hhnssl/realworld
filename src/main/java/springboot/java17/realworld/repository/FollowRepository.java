package springboot.java17.realworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.java17.realworld.entity.FollowEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

}
