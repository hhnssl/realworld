package springboot.java17.realworld.api.dto.articleDtos.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springboot.java17.realworld.entity.ArticleEntity;
import springboot.java17.realworld.entity.TagEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private String slug;
    private String title;
    private String description;
    private String body;

    // Todo: Profile 작업 후 타입 변경
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean favorited;
    private int favoritesCount;

    private List<String> tagList;

    public ArticleDto(ArticleEntity entity) {
        this.slug = entity.getSlug();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.body = entity.getBody();

        this.author = entity.getAuthor();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.favorited = entity.isFavorited();
        this.favoritesCount = entity.getFavoritesCount();

        this.tagList = entity.getTagList().stream()
            .map(TagEntity::getName)
            .collect(Collectors.toList());
    }
}
