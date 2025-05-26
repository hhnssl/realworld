package springboot.java17.realworld.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.java17.realworld.api.dto.tagDtos.response.TagsResponseDto;
import springboot.java17.realworld.service.TagService;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("")
    public ResponseEntity<TagsResponseDto> getTags(){

        TagsResponseDto tagsDto = tagService.getAllTags();

        return ResponseEntity.ok(tagsDto);
    }
}
