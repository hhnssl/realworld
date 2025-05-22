package springboot.java17.realworld.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import springboot.java17.realworld.api.dto.articleDtos.response.ArticleDto;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "articles")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleEntity {
// Todo: @NotNull 추가
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Todo: 타입 변경
    private String author;

    private String body;

    // Todo: 타입 변경
    private String comments;

    private String description;

    private boolean favorited;

    private int favoritesCount;

    private String slug;

//    private List<String> tagList;

    private String title;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public ArticleDto toDto(){
        return ArticleDto.builder()
            .slug(title)
            .title(title)
            .description(description)
            .body(body)
//            .tagList(tagList)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .favorited(favorited)
            .favoritesCount(favoritesCount)
            .author(author)
            .build();
    }

}
