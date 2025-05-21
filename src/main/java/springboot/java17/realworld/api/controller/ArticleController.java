package springboot.java17.realworld.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
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
import springboot.java17.realworld.api.dto.articleDtos.request.ArticleCreateDto;
import springboot.java17.realworld.api.dto.articleDtos.request.ArticleUpdateDto;
import springboot.java17.realworld.api.dto.articleDtos.response.ArticleDto;
import springboot.java17.realworld.api.dto.articleDtos.response.ArticleListDto;
import springboot.java17.realworld.service.ArticleServiceImpl;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleServiceImpl articleService;
    private final ObjectMapper objectMapper;

    public ArticleController(ArticleServiceImpl articleService, ObjectMapper objectMapper) {
        this.articleService = articleService;
        this.objectMapper = objectMapper;
    }


    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("ok");
    }

    @GetMapping()
    public ResponseEntity<ArticleListDto> listArticles(
        @RequestParam(required = false) Map<String, String> queryParams) {

        ArticleListDto articleListDto = articleService.getAllArticles(queryParams);

        return ResponseEntity.ok(articleListDto);
    }

    @PostMapping()
    public ResponseEntity<String> createArticle(@RequestBody ArticleCreateDto dto)
        throws JsonProcessingException {

        ArticleDto articleDto = articleService.create(dto);

        Map<String, ArticleDto> wrapper = new HashMap<>();
        wrapper.put("article", articleDto);

        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(objectMapper.writeValueAsString(wrapper));
    }

    @PutMapping("/{slug}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable String slug,
        @RequestBody ArticleUpdateDto dto) {

        ArticleDto articleDto = articleService.updateArticleBySlug(slug, dto);

        return ResponseEntity.ok(articleDto);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deleteArticle(@PathVariable String slug){

        articleService.deleteArticleBySlug(slug);

        return ResponseEntity.noContent().build();
    }

}
