package springboot.java17.realworld.api.dto.articleDtos;

import java.util.List;
import lombok.Getter;

@Getter
public class ArticleListDto {

    private List<ArticleDto> articles;
    private long articlesCount;


    public ArticleListDto(List<ArticleDto> articles, int articlesCount){
        this.articles = articles;
        this.articlesCount = articlesCount;
    }
}
