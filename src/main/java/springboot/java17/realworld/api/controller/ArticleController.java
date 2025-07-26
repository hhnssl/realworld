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

    // 임시
    @GetMapping("/{slug}/comments")
    public String getComments_temp(@PathVariable("slug") String slug) throws Exception {

        return test();
    }

    private String test() throws Exception {
        // ObjectMapper 인스턴스 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // 1. author 객체를 Map으로 생성
        // 순서를 보장하기 위해 LinkedHashMap 사용 (선택 사항)
        Map<String, Object> author = new LinkedHashMap<>();
        author.put("username", "jake");
        author.put("bio", "I work at statefarm");
        author.put("image", "https://i.stack.imgur.com/xHWG8.jpg");
        author.put("following", false);

        // 2. comment 객체를 Map으로 생성
        Map<String, Object> comment = new LinkedHashMap<>();
        comment.put("id", 1);
        comment.put("createdAt", "2016-02-18T03:22:56.637Z");
        comment.put("updatedAt", "2016-02-18T03:22:56.637Z");
        comment.put("body", "It takes a Jacobian");
        comment.put("author", author); // author 맵을 값으로 추가

        // 3. comments 배열을 List로 생성
        List<Map<String, Object>> commentsList = new ArrayList<>();
        commentsList.add(comment);

        // 4. 최상위 루트 객체를 Map으로 생성
        Map<String, Object> root = new LinkedHashMap<>();
        root.put("comments", commentsList);

        // ObjectMapper를 사용하여 Map을 JSON 문자열로 변환
        // writerWithDefaultPrettyPrinter()를 사용하면 보기 좋게 출력됩니다.
        String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);

        return jsonString;
    }


}