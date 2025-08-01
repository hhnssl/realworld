package springboot.java17.realworld.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.java17.realworld.api.dto.commentDtos.request.NewCommentRequest;
import springboot.java17.realworld.api.dto.commentDtos.response.MultipleCommentsResponse;
import springboot.java17.realworld.api.dto.commentDtos.response.SingleCommentResponse;
import springboot.java17.realworld.repository.CommentRepository;
import springboot.java17.realworld.service.CommentService;
import springboot.java17.realworld.service.CustomUserDetails;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class CommentController {

    final private CommentService commentService;

    @PostMapping("/{slug}/comments")
    public ResponseEntity<SingleCommentResponse> createComment(@PathVariable("slug") String slug,
        @Valid @RequestBody NewCommentRequest request,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        SingleCommentResponse response = commentService.createComment(slug, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{slug}/comments")
    public ResponseEntity<MultipleCommentsResponse> findAllComments(@PathVariable("slug") String slug,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        MultipleCommentsResponse response = commentService.findAllComments(slug);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
