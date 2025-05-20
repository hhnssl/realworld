package springboot.java17.realworld.api.dto.articleDtos.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import springboot.java17.realworld.entity.ArticleEntity;

@Getter
@Builder
@JsonRootName("article")
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
//    private List<String> tagList;






}
