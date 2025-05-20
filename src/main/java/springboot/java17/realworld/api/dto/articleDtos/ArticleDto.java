package springboot.java17.realworld.api.dto.articleDtos;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import springboot.java17.realworld.entity.ArticleEntity;

@Getter
public class ArticleDto {
    private String slug;
    private String title;
    private String description;
    private String body;

    // Todo: Profile 작업 후 타입 변경
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean favorited;
    private int favoritesCount;
    private String tagList;


    public ArticleDto(ArticleEntity entity){
        this.slug = entity.getSlug();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.body = entity.getBody();

        this.author = entity.getBody();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.favorited = entity.getFavorited();
        this.favoritesCount = entity.getFavoritesCount();
        this.tagList = entity.getTagList();
    }



}
