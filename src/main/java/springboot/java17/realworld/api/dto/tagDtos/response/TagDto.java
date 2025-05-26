package springboot.java17.realworld.api.dto.tagDtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import springboot.java17.realworld.entity.TagEntity;


@Builder
@ToString
@Getter
public class TagDto {

    private String name;

    public static String fromEntity(TagEntity entity){
        return entity.getName();
    }

}
