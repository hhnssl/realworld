package springboot.java17.realworld.api.dto.articleDtos.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotBlank.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springboot.java17.realworld.entity.ArticleEntity;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
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
            .description(description)
            .body(body)
            .build();
    }

}
