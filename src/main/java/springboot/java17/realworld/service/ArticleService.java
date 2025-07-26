package springboot.java17.realworld.service;

import springboot.java17.realworld.api.dto.articleDtos.request.ArticleFilterOptions;
import springboot.java17.realworld.api.dto.articleDtos.request.NewArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.request.UpdateArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.response.MultipleArticlesResponseDto;
import springboot.java17.realworld.api.dto.articleDtos.response.SingleArticleResponseDto;

public interface ArticleService {

    SingleArticleResponseDto getArticleBySlug(String slug);

    MultipleArticlesResponseDto getAllArticles(ArticleFilterOptions filter);

    SingleArticleResponseDto create(NewArticleRequestDto dto);

    SingleArticleResponseDto updateArticleBySlug(String slug, UpdateArticleRequestDto dto);

    void deleteArticleBySlug(String slug);

    MultipleArticlesResponseDto getFeedArticles();
}
