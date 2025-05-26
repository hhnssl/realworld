package springboot.java17.realworld.api.dto.articleDtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springboot.java17.realworld.entity.ArticleEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingleArticleResponseDto {
    ArticleDto article;

    public static SingleArticleResponseDto fromEntity(ArticleEntity article){
        return SingleArticleResponseDto.builder()
          .article(ArticleDto.fromEntity(article))
          .build();
    }
}
