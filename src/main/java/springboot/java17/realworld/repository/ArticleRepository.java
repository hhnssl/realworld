package springboot.java17.realworld.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.java17.realworld.entity.ArticleEntity;
import springboot.java17.realworld.entity.UserEntity;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    ArticleEntity save(ArticleEntity articleEntity);

    List<ArticleEntity> findAll();

    List<ArticleEntity> findAllByUser(UserEntity userEntity);

    List<ArticleEntity> findAllByOrderByCreatedAtDesc();

//    List<ArticleEntity`> findAllByTagList_NameOrderByCreatedAtDesc(String tagName);

    Optional<ArticleEntity> findBySlug(String slug);

    void deleteById(Long id);

}
