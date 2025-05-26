package springboot.java17.realworld.api.dto.articleDtos.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import springboot.java17.realworld.entity.ArticleEntity;
import springboot.java17.realworld.entity.TagEntity;

@Getter
@Builder
@ToString
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

    public static ArticleDto fromEntity(ArticleEntity article) {
        ArticleDto dto = ArticleDto.builder()
                .slug(article.getSlug())
                .title(article.getTitle())
                .description(article.getDescription())
                .body(article.getBody())
                .author(article.getAuthor())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .favorited(article.isFavorited())
                .favoritesCount(article.getFavoritesCount())
                .tagList(article.getTagList().stream()
                        .map(TagEntity::getName)
                        .collect(Collectors.toList()))
                .build();

        return dto;
    }
}
