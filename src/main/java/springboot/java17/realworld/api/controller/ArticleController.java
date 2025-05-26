package springboot.java17.realworld.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springboot.java17.realworld.api.dto.articleDtos.request.NewArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.request.UpdateArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.response.MultipleArticlesResponseDto;
import springboot.java17.realworld.api.dto.articleDtos.response.SingleArticleResponseDto;
import springboot.java17.realworld.service.ArticleService;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
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

    @PostMapping("")
    public ResponseEntity<SingleArticleResponseDto> createArticle(@RequestBody() NewArticleRequestDto dto) {

        SingleArticleResponseDto articleDto = articleService.create(dto);

        return ResponseEntity.ok(articleDto);
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

}
