package springboot.java17.realworld.service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;
import springboot.java17.realworld.api.dto.articleDtos.request.NewArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.request.UpdateArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.response.ArticleDto;
import springboot.java17.realworld.api.dto.articleDtos.response.MultipleArticlesResponseDto;
import springboot.java17.realworld.api.dto.articleDtos.response.SingleArticleResponseDto;
import springboot.java17.realworld.entity.ArticleEntity;
import springboot.java17.realworld.repository.ArticleRepository;
import springboot.java17.realworld.repository.TagRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public SingleArticleResponseDto getArticleBySlug(String slug) {

        ArticleEntity article = articleRepository.findBySlug(slug)
            .orElseThrow(() -> new IllegalArgumentException("검색 결과 없음"));

        return SingleArticleResponseDto.fromEntity(article);
    }

    @Override
    public MultipleArticlesResponseDto getAllArticles(String author, String tag) {
        List<ArticleEntity> articleList;

        if (!author.isEmpty()) {
            articleList = articleRepository.findAllByAuthorOrderByCreatedAtDesc(author);
        } else if (!tag.isEmpty()) {
            articleList = articleRepository.findAllByTagList_NameOrderByCreatedAtDesc(tag);
        } else {
            articleList = articleRepository.findAllByOrderByCreatedAtDesc();
        }

        List<ArticleDto> articleDtoList = articleList.stream()
            .map(ArticleDto::fromEntity)
            .toList();

        return MultipleArticlesResponseDto.builder()
            .articles(articleDtoList)
            .articlesCount(articleDtoList.size())
            .build();
    }

    @Override
    public SingleArticleResponseDto create(NewArticleRequestDto dto) {
        ArticleEntity article = dto.toEntity();

        articleRepository.save(article);

        return SingleArticleResponseDto.fromEntity(article);
    }

    @Override
    public SingleArticleResponseDto updateArticleBySlug(String slug, UpdateArticleRequestDto dto) {

        // Todo: slug로 검색한 결과가 존재하지 않을 경우 예외 처리
        ArticleEntity article = articleRepository.findBySlug(slug)
            .orElseThrow(() -> new IllegalArgumentException("검색 결과 없음"));

        article.setSlug(dto.getTitle() == null ? article.getSlug() : dto.getTitle());
        article.setTitle(dto.getTitle() == null ? article.getSlug() : dto.getTitle());
        article.setDescription(
            dto.getDescription() == null ? article.getDescription() : dto.getDescription());
        article.setBody(dto.getBody() == null ? article.getBody() : dto.getBody());

        articleRepository.save(article);

        return SingleArticleResponseDto.fromEntity(article);
    }

    @Override
    @Transactional
    public void deleteArticleBySlug(String slug) {
        ArticleEntity entity = articleRepository.findBySlug(slug)
            .orElseThrow(() -> new IllegalArgumentException("검색 결과 없음"));

        // 삭제
        articleRepository.deleteById(entity.getId());
    }
}
