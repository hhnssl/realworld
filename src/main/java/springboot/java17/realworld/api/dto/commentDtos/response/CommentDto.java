package springboot.java17.realworld.api.dto.commentDtos.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import springboot.java17.realworld.api.dto.profileDtos.response.ProfileDto;
import springboot.java17.realworld.entity.CommentEntity;

@Getter
@Builder
public class CommentDto {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String body;

    private ProfileDto author;


    public static CommentDto fromEntity(CommentEntity comment, ProfileDto profile){
        return CommentDto.builder()
            .id(comment.getId())
            .createdAt(comment.getCreatedAt())
            .updatedAt(comment.getUpdatedAt())
            .body(comment.getBody())
            .author(profile)
            .build();
    }
}
