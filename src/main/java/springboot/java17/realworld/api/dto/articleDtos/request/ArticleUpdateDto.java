package springboot.java17.realworld.api.dto.articleDtos.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("article")
public class ArticleUpdateDto {
    private String title;

    private String description;

    private String body;
}
