package springboot.java17.realworld.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import springboot.java17.realworld.api.dto.articleDtos.ArticleDto;
import springboot.java17.realworld.api.dto.articleDtos.ArticleListDto;
import springboot.java17.realworld.repository.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    public ArticleListDto getAllArticles(Map<String, String> queryParams){

        List<ArticleDto> articleDtoList = articleRepository.findAll()
            .stream()
            .map(entity -> {
                return new ArticleDto(entity);
            })
            .toList();

        return new ArticleListDto(articleDtoList, articleDtoList.size());
    }

}
