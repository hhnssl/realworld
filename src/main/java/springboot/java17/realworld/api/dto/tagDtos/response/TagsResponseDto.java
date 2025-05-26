package springboot.java17.realworld.api.dto.tagDtos.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class TagsResponseDto {

    private List<String> tags;

}
