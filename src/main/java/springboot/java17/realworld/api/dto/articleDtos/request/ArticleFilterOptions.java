package springboot.java17.realworld.api.dto.articleDtos.request;

import lombok.Data;

@Data
public class ArticleFilterOptions {

    private String tag;

    private String author;

    private String favorited;

    private Long limit;

    private Long offset;
}
