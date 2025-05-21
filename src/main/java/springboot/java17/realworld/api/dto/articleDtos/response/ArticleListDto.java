package springboot.java17.realworld.api.dto.articleDtos.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListDto {

    private List<ArticleDto> articles;

    private long articlesCount;


    public ArticleListDto(List<ArticleDto> articles, int articlesCount){
        this.articles = articles;
        this.articlesCount = articlesCount;
    }
}
