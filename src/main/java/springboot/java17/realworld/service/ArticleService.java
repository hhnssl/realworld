package springboot.java17.realworld.service;

import java.util.Map;
import springboot.java17.realworld.api.dto.articleDtos.request.ArticleCreateDto;
import springboot.java17.realworld.api.dto.articleDtos.response.ArticleDto;
import springboot.java17.realworld.api.dto.articleDtos.response.ArticleListDto;

public interface ArticleService {
    ArticleListDto getAllArticles(Map<String, String> queryParams);

    ArticleDto create(ArticleCreateDto dto);

}
