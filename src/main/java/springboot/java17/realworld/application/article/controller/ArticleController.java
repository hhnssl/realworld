package springboot.java17.realworld.application.article.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("ok");
    }
}
