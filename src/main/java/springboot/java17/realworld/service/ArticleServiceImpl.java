package springboot.java17.realworld.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import springboot.java17.realworld.api.dto.articleDtos.request.ArticleCreateDto;
import springboot.java17.realworld.api.dto.articleDtos.request.ArticleUpdateDto;
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

    @Override
    public ArticleDto updateArticleBySlug(String slug, ArticleUpdateDto dto) {

        // Todo: slug로 검색한 결과가 존재하지 않을 경우 예외 처리
        ArticleEntity article = articleRepository.findBySlug(slug)
            .orElseThrow(() -> new IllegalArgumentException("검색 결과 없음"));

        article.setSlug(dto.getTitle() == null ? article.getSlug() : dto.getTitle());
        article.setTitle(dto.getTitle() == null ? article.getSlug() : dto.getTitle());
        article.setDescription(
            dto.getDescription() == null ? article.getDescription() : dto.getDescription());
        article.setBody(dto.getBody() == null ? article.getBody() : dto.getBody());

        return articleRepository.save(article).toDto();
    }

    @Override
    @Transactional
    public void deleteArticleBySlug(String slug) {
        articleRepository.findBySlug(slug)
            .orElseThrow(() -> new IllegalArgumentException("검색 결과 없음"));

        // 삭제
        articleRepository.deleteBySlug(slug);
    }
}
