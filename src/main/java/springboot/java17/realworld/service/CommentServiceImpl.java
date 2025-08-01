package springboot.java17.realworld.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import springboot.java17.realworld.api.dto.commentDtos.request.NewCommentRequest;
import springboot.java17.realworld.api.dto.commentDtos.response.MultipleCommentsResponse;
import springboot.java17.realworld.api.dto.commentDtos.response.SingleCommentResponse;
import springboot.java17.realworld.api.dto.profileDtos.response.ProfileDto;
import springboot.java17.realworld.api.exception.ArticleNotFoundException;
import springboot.java17.realworld.entity.ArticleEntity;
import springboot.java17.realworld.entity.CommentEntity;
import springboot.java17.realworld.entity.UserEntity;
import springboot.java17.realworld.repository.ArticleRepository;
import springboot.java17.realworld.repository.CommentRepository;
import springboot.java17.realworld.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ProfileServiceImpl profileServiceImpl;

    public SingleCommentResponse createComment(String slug, NewCommentRequest request) {

        UserEntity commentWriter = getCurrentUser().get();
        ArticleEntity targetArticle = getCurrentArticle(slug);

        CommentEntity newComment = CommentEntity.builder()
            .body(request.getBody())
            .user(commentWriter)
            .article(targetArticle)
            .build();

        commentRepository.save(newComment);

        return SingleCommentResponse.fromEntity(newComment,
            ProfileDto.fromEntity(commentWriter, false));
    }

    @Override
    public MultipleCommentsResponse findAllComments(String slug) {

        return null;
    }

    /**/
    private Optional<UserEntity> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(
            authentication.getPrincipal())) {
            return java.util.Optional.empty();
        }
        String userEmail = authentication.getName();

        return userRepository.findByEmail(userEmail);
    }

    private ArticleEntity getCurrentArticle(String slug) {
        return articleRepository.findBySlug(slug)
            .orElseThrow(() -> new ArticleNotFoundException("존재하지 않는 게시글 입니다."));
    }


}
