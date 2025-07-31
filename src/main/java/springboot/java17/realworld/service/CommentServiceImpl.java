package springboot.java17.realworld.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.java17.realworld.api.dto.commentDtos.request.NewCommentRequest;
import springboot.java17.realworld.api.dto.commentDtos.response.SingleCommentResponse;
import springboot.java17.realworld.api.dto.profileDtos.response.ProfileDto;
import springboot.java17.realworld.api.exception.ArticleNotFoundException;
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
        UserEntity user = getCurrentUser();
        Long articleId = getArticleId(slug);

        CommentEntity comment = CommentEntity.builder()
            .body(request.getBody())
            .userId(user.getId())
            .articleId(articleId)
            .build();

        CommentEntity addedComment = commentRepository.save(comment);
        ProfileDto profile = profileServiceImpl.getProfileByUsername(user.getUsername())
            .getProfile();

        return SingleCommentResponse.fromEntity(addedComment, profile);
    }



    /**/
    private UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        return userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new UsernameNotFoundException("인증된 사용자를 찾을 수 없습니다: " + userEmail));
    }

    private Long getArticleId(String slug) {
        return articleRepository.findBySlug(slug)
            .orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다.")).getId();
    }


}
