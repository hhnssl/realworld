package springboot.java17.realworld.api.dto.articleDtos.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springboot.java17.realworld.entity.ArticleEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("article")
public class ArticleCreateDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String body;

//    private List<String> tagList;

    public ArticleEntity toEntity(){
        return ArticleEntity.builder()
            .title(title)
            .slug(title)
            .description(description)
            .body(body)
            .build();
    }

}
