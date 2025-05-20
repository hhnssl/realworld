package springboot.java17.realworld.api.dto.articleDtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.List;
import lombok.Getter;

@Getter
public class ArticleListDto {

    @JsonProperty("articles")
    private List<ArticleDto> articles;

    @JsonProperty("articlesCount")
    private long articlesCount;


    public ArticleListDto(List<ArticleDto> articles, int articlesCount){
        this.articles = articles;
        this.articlesCount = articlesCount;
    }
}
