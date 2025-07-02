package springboot.java17.realworld.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import springboot.java17.realworld.api.dto.tagDtos.response.TagDto;
import springboot.java17.realworld.api.dto.tagDtos.response.TagsResponseDto;
import springboot.java17.realworld.entity.TagEntity;
import springboot.java17.realworld.repository.TagRepository;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagsResponseDto getAllTags() {

        List<TagEntity> tagList = tagRepository.findAll();

        List<String> tags = tagList.stream()
            .map(TagDto::fromEntity)
            .toList();

        return TagsResponseDto.builder()
            .tags(tags)
            .build();
    }

    @Override
    public List<TagEntity> saveAll(List<String> tags) {

        List<TagEntity> tagList = tags.stream()
            .map(tag -> new TagEntity(tag))
            .toList();

        tagRepository.saveAll(tagList);

        return tagList;
    }

    @Override
    public void delete(TagEntity tag) {
        tagRepository.delete(tag);
    }
}
