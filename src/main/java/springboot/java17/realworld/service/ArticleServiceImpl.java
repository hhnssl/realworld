package springboot.java17.realworld.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import springboot.java17.realworld.api.dto.articleDtos.request.NewArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.request.UpdateArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.response.ArticleDto;
import springboot.java17.realworld.api.dto.articleDtos.response.MultipleArticlesResponseDto;
import springboot.java17.realworld.api.dto.articleDtos.response.SingleArticleResponseDto;
import springboot.java17.realworld.entity.ArticleEntity;
import springboot.java17.realworld.entity.ArticleTag;
import springboot.java17.realworld.entity.TagEntity;
import springboot.java17.realworld.entity.UserEntity;
import springboot.java17.realworld.repository.ArticleRepository;
import springboot.java17.realworld.repository.ArticleTagRepository;
import springboot.java17.realworld.repository.TagRepository;
import springboot.java17.realworld.repository.UserRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleTagRepository articleTagRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    // Todo: Auth 구현 후 삭제
    UserEntity testUSer = UserEntity.builder()
        .email("user1@gmail.com")
        .username("user1")
        .password("password")
        .build();


    public ArticleServiceImpl(ArticleRepository articleRepository, TagRepository tagRepository,
        UserRepository userRepository,
        ArticleTagRepository articleTagRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.articleTagRepository = articleTagRepository;
    }

    @Override
    public SingleArticleResponseDto getArticleBySlug(String slug) {

        ArticleEntity article = articleRepository.findBySlug(slug)
            .orElseThrow(() -> new IllegalArgumentException("검색 결과 없음"));

        List<ArticleTag> articleTags = articleTagRepository.findAllByArticle(article);

        List<TagEntity> tags = new ArrayList<>();

        for(ArticleTag articleTag: articleTags){
            tags.add(articleTag.getTag());
        }


        return SingleArticleResponseDto.fromEntity(article, tags);
    }

    @Override
    public MultipleArticlesResponseDto getAllArticles(String author, String tag) {
        List<ArticleEntity> articleList;

        if (!author.isEmpty()) {
            // Todo
            UserEntity user = userRepository.findByUsername(author)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 username 입니다."));

            articleList = articleRepository.findAllByUser(user);
        } else if (!tag.isEmpty()) {
            articleList = articleRepository.findAllByOrderByCreatedAtDesc();
        } else {
            articleList = articleRepository.findAllByOrderByCreatedAtDesc();
        }


        List<ArticleDto> articleDtoList = new ArrayList<>();


        for(ArticleEntity article: articleList){
            List<ArticleTag> articleTags = articleTagRepository.findAllByArticle(article);
            List<TagEntity> tags = new ArrayList<>();

            for(ArticleTag articleTag: articleTags){
                tags.add(articleTag.getTag());
            }
            articleDtoList.add(ArticleDto.fromEntity(article, tags));
        }


        return MultipleArticlesResponseDto.builder()
            .articles(articleDtoList)
            .articlesCount(articleDtoList.size())
            .build();
    }

    @Override
    public SingleArticleResponseDto create(NewArticleRequestDto dto) {
        ArticleEntity article = dto.toEntity();

        // Todo: 현재 로그인한 유저의 정보로 변경할 것
        userRepository.save(testUSer);
        article.setUser(testUSer);
        //

        // Article 저장
        articleRepository.save(article);

        // Tag 목록 저장
        List<TagEntity> tags = saveTags(dto.getTagList());
        tagRepository.saveAll(tags);

        // Article과 Tag들 연결 및 저장
        linkTagsToArticle(tags, article);

        return SingleArticleResponseDto.fromEntity(article, tags);
    }

    private List<TagEntity> saveTags(List<String> tags) {
        return tags.stream()
            .map(tag -> new TagEntity(tag))
            .collect(Collectors.toList());
    }

    private void linkTagsToArticle(List<TagEntity> tags, ArticleEntity article) {
        tags.forEach(tag -> {
            ArticleTag articleTag = new ArticleTag();

            articleTag.setArticle(article);
            articleTag.setTag(tag);
            articleTagRepository.save(articleTag);
        });
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

        List<ArticleTag> articleTags = articleTagRepository.findAllByArticle(article);
        List<TagEntity> tags = new ArrayList<>();
        for (ArticleTag articleTag : articleTags) {
            tags.add(articleTag.getTag());
        }


        return SingleArticleResponseDto.fromEntity(article, tags);
    }
    @Override
    @Transactional
    public void deleteArticleBySlug(String slug) {
        ArticleEntity entity = articleRepository.findBySlug(slug)
            .orElseThrow(() -> new IllegalArgumentException("검색 결과 없음"));

        // 삭제
        articleRepository.deleteById(entity.getId());
    }

    @Override
    public MultipleArticlesResponseDto getFeedArticles() {
        UserEntity me = userRepository.findByUsername("dataUser")
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // me가 팔로우하고 있는 목록
//        List<FollowEntity> followings = followService.getFollowingList(me);

//        List<List<ArticleEntity>> obj = followings.stream()
//            .map(item -> articleRepository.findAllByUser(item.getFollowing()))
//            .toList();

//        List<ArticleDto> articleDtoList = obj.stream()
//            .flatMap(List::stream)
//            .map(ArticleDto::fromEntity)
//            .collect(Collectors.toList());
//
//        return MultipleArticlesResponseDto.builder()
//            .articles(articleDtoList)
//            .articlesCount(articleDtoList.size())
//            .build();
        return null;
    }
}
