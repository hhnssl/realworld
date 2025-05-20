package springboot.java17.realworld.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import springboot.java17.realworld.api.dto.articleDtos.request.ArticleCreateDto;
import springboot.java17.realworld.api.dto.articleDtos.response.ArticleDto;
import springboot.java17.realworld.api.dto.articleDtos.response.ArticleListDto;
import springboot.java17.realworld.entity.ArticleEntity;
import springboot.java17.realworld.repository.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    // Todo: Map -> QeuryParam 객체 생성
    public ArticleListDto getAllArticles(Map<String, String> queryParams) {

        List<ArticleDto> articleDtoList = articleRepository.findAll()
            .stream()
            .map(ArticleEntity::toDto)
            .toList();

        return new ArticleListDto(articleDtoList, articleDtoList.size());
    }

    @Override
    public ArticleDto create(ArticleCreateDto dto) {

        ArticleDto newArticle = articleRepository.save(dto.toEntity()).toDto();

        return newArticle;
    }

}
