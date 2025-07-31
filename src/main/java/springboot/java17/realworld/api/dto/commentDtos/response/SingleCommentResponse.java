package springboot.java17.realworld.api.dto.commentDtos.response;

import lombok.Builder;
import lombok.Getter;
import springboot.java17.realworld.api.dto.profileDtos.response.ProfileDto;
import springboot.java17.realworld.entity.CommentEntity;

@Getter
@Builder
public class SingleCommentResponse {

    private CommentDto comment;

    public static SingleCommentResponse fromEntity(CommentEntity comment, ProfileDto profile){
        return SingleCommentResponse.builder()
            .comment(CommentDto.fromEntity(comment, profile))
            .build();
    }
}
