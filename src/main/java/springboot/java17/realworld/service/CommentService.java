package springboot.java17.realworld.service;

import springboot.java17.realworld.api.dto.commentDtos.request.NewCommentRequest;
import springboot.java17.realworld.api.dto.commentDtos.response.MultipleCommentsResponse;
import springboot.java17.realworld.api.dto.commentDtos.response.SingleCommentResponse;

public interface CommentService {
    SingleCommentResponse createComment(String slug, NewCommentRequest request);

    MultipleCommentsResponse findAllComments(String slug);
}
