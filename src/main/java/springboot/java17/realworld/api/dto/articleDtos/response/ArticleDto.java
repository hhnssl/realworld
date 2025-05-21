package springboot.java17.realworld.api.dto.articleDtos.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private boolean favorited;
    private int favoritesCount;
//    private List<String> tagList;






}
