package springboot.java17.realworld.service;

import java.util.List;
import springboot.java17.realworld.api.dto.tagDtos.response.TagsResponseDto;
import springboot.java17.realworld.entity.TagEntity;

public interface TagService {

    TagsResponseDto getAllTags();

    List<TagEntity> saveAll(List<String> tags);

    void delete(TagEntity tag);

}
