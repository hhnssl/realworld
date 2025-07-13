package springboot.java17.realworld.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.java17.realworld.api.dto.articleDtos.request.NewArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.request.UpdateArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.response.ArticleDto;
import springboot.java17.realworld.api.dto.articleDtos.response.MultipleArticlesResponseDto;
import springboot.java17.realworld.api.dto.articleDtos.response.SingleArticleResponseDto;
import springboot.java17.realworld.entity.ArticleEntity;
import springboot.java17.realworld.entity.ArticleTag;
import springboot.java17.realworld.entity.FollowEntity;
import springboot.java17.realworld.entity.TagEntity;
import springboot.java17.realworld.entity.UserEntity;
import springboot.java17.realworld.repository.ArticleRepository;
import springboot.java17.realworld.repository.ArticleTagRepository;
import springboot.java17.realworld.repository.FollowRepository;
import springboot.java17.realworld.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {


    private final ArticleRepository articleRepository;
    private final ArticleTagRepository articleTagRepository;
    private final TagService tagService;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;


    @Override
    @Transactional(readOnly = true)
    public SingleArticleResponseDto getArticleBySlug(String slug) {
        ArticleEntity article = findArticleBySlug(slug);

        List<TagEntity> tags = extractTagsFromArticle(article);

        return SingleArticleResponseDto.fromEntity(article, tags);
    }


    @Override
    @Transactional(readOnly = true)
    public MultipleArticlesResponseDto getAllArticles(String author, String tag) {
        List<ArticleEntity> articleList;

        if (author != null && !author.isEmpty()) {
            UserEntity user = userRepository.findByUsername(author)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 username 입니다."));

            articleList = articleRepository.findAllByUserWithDetails(user);
        } else if (tag != null && !tag.isEmpty()) {
            articleList = articleRepository.findAllByTag(tag);
        } else {
            articleList = articleRepository.findAllWithDetails();
        }

        List<ArticleDto> articleDtoList = articleList.stream()
            .map(article -> ArticleDto.fromEntity(article, extractTagsFromArticle(article)))
            .collect(Collectors.toList());

        return new MultipleArticlesResponseDto(articleDtoList, articleDtoList.size());
    }

    @Override
    @Transactional
    public SingleArticleResponseDto create(NewArticleRequestDto dto) {
        UserEntity currentUser = getCurrentUser();
        ArticleEntity article = dto.toEntity();
        article.setAuthor(currentUser);

        // Article 저장 (이때 @PrePersist에 의해 slug 자동 생성됨)
        articleRepository.save(article);

        // Tag 저장 및 연결
        List<TagEntity> tags = tagService.saveAll(dto.getTagList());
        linkTagsToArticle(tags, article);

        return SingleArticleResponseDto.fromEntity(article, tags);
    }


    @Override
    @Transactional
    public SingleArticleResponseDto updateArticleBySlug(String slug, UpdateArticleRequestDto dto) {
        ArticleEntity article = findArticleBySlug(slug);

        if (!article.getUser().equals(getCurrentUser())) {
            throw new AccessDeniedException("이 게시글을 수정할 권한이 없습니다.");
        }

        article.update(dto.getTitle(), dto.getDescription(), dto.getBody());
        articleRepository.save(article);

        List<TagEntity> tags = extractTagsFromArticle(article);
        return SingleArticleResponseDto.fromEntity(article, tags);
    }

    @Override
    @Transactional
    public void deleteArticleBySlug(String slug) {
        ArticleEntity article = findArticleBySlug(slug);

        if (!article.getUser().equals(getCurrentUser())) {
            throw new AccessDeniedException("이 게시글을 삭제할 권한이 없습니다.");
        }

        // 연관된 ArticleTag를 먼저 삭제
        articleTagRepository.deleteAll(article.getArticleTags());

        articleRepository.delete(article);
    }

    @Override
    @Transactional(readOnly = true)
    public MultipleArticlesResponseDto getFeedArticles() {
        // 1. 현재 인증된 사용자 조회
        UserEntity currentUser = getCurrentUser();

        // 2. 현재 사용자가 팔로우하는 모든 관계 조회
        List<FollowEntity> followings = followRepository.findAllByFollower(currentUser);

        // 3. 만약 팔로우하는 사람이 없다면 빈 피드 반환
        if (followings.isEmpty()) {
            return new MultipleArticlesResponseDto(Collections.emptyList(), 0);
        }

        // 4. 팔로우하는 작성자 목록 추출
        List<UserEntity> followedAuthors = followings.stream()
            .map(FollowEntity::getFollowing)
            .collect(Collectors.toList());

        // 5. 팔로우하는 모든 작성자의 게시글을 최신순으로 한 번에 조회
        List<ArticleEntity> feedArticles = articleRepository.findArticlesByAuthors(followedAuthors);

        // 6. DTO로 변환하여 반환
        List<ArticleDto> articleDtoList = feedArticles.stream()
            .map(article -> ArticleDto.fromEntity(article, extractTagsFromArticle(article)))
            .collect(Collectors.toList());

        return new MultipleArticlesResponseDto(articleDtoList, articleDtoList.size());
    }

    private UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userEmail = authentication.getName();

        return userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new UsernameNotFoundException("인증된 사용자를 찾을 수 없습니다: " + userEmail));
    }

    private ArticleEntity findArticleBySlug(String slug) {
        return articleRepository.findBySlug(slug)
            .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다: " + slug));
    }

    private void linkTagsToArticle(List<TagEntity> tags, ArticleEntity article) {
        tags.forEach(tag -> {
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticle(article);
            articleTag.setTag(tag);
            articleTagRepository.save(articleTag);
        });
    }

    private List<TagEntity> extractTagsFromArticle(ArticleEntity article) {
        return article.getArticleTags()
            .stream()
            .map(ArticleTag::getTag)
            .collect(Collectors.toList());
    }
}
