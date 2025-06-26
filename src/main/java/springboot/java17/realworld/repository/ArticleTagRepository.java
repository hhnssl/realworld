package springboot.java17.realworld.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.java17.realworld.entity.ArticleEntity;
import springboot.java17.realworld.entity.ArticleTag;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    List<ArticleTag> findAllByArticle(ArticleEntity article);
}
