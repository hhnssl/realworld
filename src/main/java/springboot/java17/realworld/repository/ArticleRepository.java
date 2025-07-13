package springboot.java17.realworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springboot.java17.realworld.entity.ArticleEntity;
import springboot.java17.realworld.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    Optional<ArticleEntity> findBySlug(String slug);

    @Query("SELECT DISTINCT a FROM ArticleEntity a JOIN FETCH a.user u LEFT JOIN FETCH a.articleTags at LEFT JOIN FETCH at.tag WHERE u = :user")
    List<ArticleEntity> findAllByUserWithDetails(@Param("user") UserEntity user);

    @Query("SELECT DISTINCT a FROM ArticleEntity a JOIN FETCH a.user u LEFT JOIN FETCH a.articleTags at LEFT JOIN FETCH at.tag")
    List<ArticleEntity> findAllWithDetails();

    @Query("SELECT DISTINCT a FROM ArticleEntity a JOIN FETCH a.user u LEFT JOIN FETCH a.articleTags at LEFT JOIN FETCH at.tag t WHERE t.name = :tagName")
    List<ArticleEntity> findAllByTag(@Param("tagName") String tagName);

    // getFeedArticles()를 위해 추가된 메서드
    @Query("SELECT DISTINCT a FROM ArticleEntity a " +
        "JOIN FETCH a.user u " +
        "LEFT JOIN FETCH a.articleTags at " +
        "LEFT JOIN FETCH at.tag " +
        "WHERE u IN :authors " +
        "ORDER BY a.createdAt DESC")
    List<ArticleEntity> findArticlesByAuthors(@Param("authors") List<UserEntity> authors);
}