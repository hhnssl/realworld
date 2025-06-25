package springboot.java17.realworld.api.dto.articleDtos.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
@JsonRootName("article")
public class NewArticleRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String body;

    private List<String> tagList = new ArrayList<>();

    public ArticleEntity toEntity() {
        return ArticleEntity.builder()
            .title(title)
            .slug(title)
            .description(description)
            .body(body)
            .build();
    }
}
