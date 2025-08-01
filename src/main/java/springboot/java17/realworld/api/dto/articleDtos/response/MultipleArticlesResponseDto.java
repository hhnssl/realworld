package springboot.java17.realworld.api.dto.articleDtos.response;

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

    private List<ArticleDto> articles;

    private long articlesCount;
}
