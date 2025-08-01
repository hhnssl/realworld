package springboot.java17.realworld.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.java17.realworld.api.dto.articleDtos.request.ArticleFilterOptions;
import springboot.java17.realworld.api.dto.articleDtos.request.NewArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.request.UpdateArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.response.MultipleArticlesResponseDto;
import springboot.java17.realworld.api.dto.articleDtos.response.SingleArticleResponseDto;
import springboot.java17.realworld.service.ArticleService;
import springboot.java17.realworld.service.CustomUserDetails;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;


    @GetMapping("/{slug}")
    public ResponseEntity<SingleArticleResponseDto> getArticle(@PathVariable("slug") String slug) {

        SingleArticleResponseDto response = articleService.getArticleBySlug(slug);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<MultipleArticlesResponseDto> listArticles(ArticleFilterOptions filter) {

        MultipleArticlesResponseDto response = articleService.getAllArticles(filter);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SingleArticleResponseDto> createArticle(
        @Valid @RequestBody NewArticleRequestDto dto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        SingleArticleResponseDto response = articleService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{slug}")
    public ResponseEntity<SingleArticleResponseDto> updateArticle(
        @PathVariable("slug") String slug,
        @RequestBody UpdateArticleRequestDto dto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        SingleArticleResponseDto response = articleService.updateArticleBySlug(slug, dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deleteArticle(
        @PathVariable("slug") String slug,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        articleService.deleteArticleBySlug(slug);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/feed")
    public ResponseEntity<MultipleArticlesResponseDto> feedArticles(
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        MultipleArticlesResponseDto response = articleService.getFeedArticles();

        return ResponseEntity.ok(response);
    }
}