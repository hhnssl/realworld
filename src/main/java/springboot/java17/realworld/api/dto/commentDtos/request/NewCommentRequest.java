package springboot.java17.realworld.api.dto.commentDtos.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@JsonRootName("comment")
public class NewCommentRequest {

    @NotBlank
    private String body;

    @Builder
    public NewCommentRequest(String body) {
        this.body = body;
    }
}
