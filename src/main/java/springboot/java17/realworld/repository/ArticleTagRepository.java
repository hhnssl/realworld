package springboot.java17.realworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.java17.realworld.entity.ArticleTag;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {

}
