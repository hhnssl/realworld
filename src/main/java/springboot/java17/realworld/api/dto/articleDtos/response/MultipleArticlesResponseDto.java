package springboot.java17.realworld.api.dto.articleDtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MultipleArticlesResponseDto {

    @JsonProperty("articles")
    private List<ArticleDto> articles;

    @JsonProperty("articlesCount")
    private long articlesCount;
}
