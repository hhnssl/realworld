package springboot.java17.realworld.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.java17.realworld.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByArticleId(Long articleId);
}
