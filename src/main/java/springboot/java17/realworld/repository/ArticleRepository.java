package springboot.java17.realworld.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.java17.realworld.entity.ArticleEntity;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    ArticleEntity save(ArticleEntity articleEntity);

    List<ArticleEntity> findAll();

    List<ArticleEntity> findAllByOrderByCreatedAtDesc();

    List<ArticleEntity> findAllByAuthorOrderByCreatedAtDesc(String author);

    Optional<ArticleEntity> findBySlug(String slug);

    void deleteBySlug(String slug);

}
