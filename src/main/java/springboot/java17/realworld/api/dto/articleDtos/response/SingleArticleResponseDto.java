package springboot.java17.realworld.api.dto.articleDtos.response;

import java.util.List;
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
public class SingleArticleResponseDto {
    ArticleDto article;

    public static SingleArticleResponseDto fromEntity(ArticleEntity article, List<TagEntity> tagList){
        return SingleArticleResponseDto.builder()
          .article(ArticleDto.fromEntity(article, tagList))
          .build();
    }
}
