package springboot.java17.realworld.service;

import java.util.Map;
import springboot.java17.realworld.api.dto.articleDtos.request.ArticleCreateDto;
import springboot.java17.realworld.api.dto.articleDtos.request.ArticleUpdateDto;
import springboot.java17.realworld.api.dto.articleDtos.response.ArticleDto;
import springboot.java17.realworld.api.dto.articleDtos.response.ArticleListDto;

public interface ArticleService {

    ArticleDto getArticleBySlug(String slug);

    ArticleListDto getAllArticles(String author);

    ArticleDto create(ArticleCreateDto dto);

    ArticleDto updateArticleBySlug(String slug, ArticleUpdateDto dto);

    void deleteArticleBySlug(String slug);
}
