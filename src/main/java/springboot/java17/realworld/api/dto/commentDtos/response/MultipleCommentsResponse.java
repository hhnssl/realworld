package springboot.java17.realworld.api.dto.commentDtos.response;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MultipleCommentsResponse {

    private List<CommentDto> comments;

}
