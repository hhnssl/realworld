package springboot.java17.realworld.api.dto.articleDtos.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import springboot.java17.realworld.api.dto.profileDtos.response.ProfileDto;
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

    private ProfileDto author;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean favorited;
    private int favoritesCount;

    private List<String> tagList;

    public static ArticleDto fromEntity(ArticleEntity article, List<TagEntity> tagList) {
        ArticleDto dto = ArticleDto.builder()
                .slug(article.getSlug())
                .title(article.getTitle())
                .description(article.getDescription())
                .body(article.getBody())
                .author(ProfileDto.fromEntity(article.getUser(), false))
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .favorited(null) //Todo
                .favoritesCount(0) //Todo
                .tagList(tagList.stream()
                        .map(TagEntity::getName)
                        .collect(Collectors.toList()))
                .build();

        return dto;
    }


}
