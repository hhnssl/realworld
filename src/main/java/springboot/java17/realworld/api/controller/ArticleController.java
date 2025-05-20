package springboot.java17.realworld.api.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springboot.java17.realworld.api.dto.articleDtos.ArticleListDto;
import springboot.java17.realworld.service.ArticleServiceImpl;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleServiceImpl articleService;

    public ArticleController(ArticleServiceImpl articleService){
        this.articleService = articleService;
    }


    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("ok");
    }

    @GetMapping()
    public ResponseEntity<ArticleListDto> listArticles(@RequestParam(required = false) Map<String, String> queryParams) {

        ArticleListDto articleListDto = articleService.getAllArticles(queryParams);

        return ResponseEntity.ok(articleListDto);
    }
}
