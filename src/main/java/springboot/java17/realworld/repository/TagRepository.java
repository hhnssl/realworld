package springboot.java17.realworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.java17.realworld.entity.TagEntity;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

}
