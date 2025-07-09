package springboot.java17.realworld.api.controller;

import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springboot.java17.realworld.api.dto.articleDtos.request.NewArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.request.UpdateArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.response.MultipleArticlesResponseDto;
import springboot.java17.realworld.api.dto.articleDtos.response.SingleArticleResponseDto;
import springboot.java17.realworld.service.ArticleService;
import springboot.java17.realworld.service.UserService;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestHeader("AUTHORIZATION") String authorization) {

        Object obj = authorization;

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/{slug}")
    public ResponseEntity<SingleArticleResponseDto> getArticle(@PathVariable("slug") String slug) {
        SingleArticleResponseDto articleDto = articleService.getArticleBySlug(slug);

        return ResponseEntity.ok(articleDto);
    }

    @GetMapping("")
    public ResponseEntity<MultipleArticlesResponseDto> listArticles(
        @RequestParam(name = "author", required = false, defaultValue = "") String author,
        @RequestParam(name = "tag", required = false, defaultValue = "") String tag) {

        MultipleArticlesResponseDto articleListDto = articleService.getAllArticles(author, tag);

        return ResponseEntity.ok(articleListDto);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<SingleArticleResponseDto> createArticle(@Valid @RequestBody() NewArticleRequestDto dto) {
        // token 검증
        SingleArticleResponseDto articleDto = articleService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(articleDto);
    }

    @PutMapping("/{slug}")
    public ResponseEntity<SingleArticleResponseDto> updateArticle(@PathVariable("slug") String slug,
        @RequestBody() UpdateArticleRequestDto dto) {

        SingleArticleResponseDto articleDto = articleService.updateArticleBySlug(slug, dto);

        return ResponseEntity.ok(articleDto);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("slug") String slug) {

        articleService.deleteArticleBySlug(slug);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/feed")
    public ResponseEntity<MultipleArticlesResponseDto> feedArticles(){

        MultipleArticlesResponseDto articleListDto = articleService.getFeedArticles();

        return ResponseEntity.ok(articleListDto);
    }
}
