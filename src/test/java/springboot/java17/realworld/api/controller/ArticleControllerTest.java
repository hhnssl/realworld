package springboot.java17.realworld.api.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import springboot.java17.realworld.api.dto.articleDtos.request.NewArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.request.UpdateArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.response.ArticleDto;
import springboot.java17.realworld.api.dto.articleDtos.response.SingleArticleResponseDto;
import springboot.java17.realworld.entity.ArticleEntity;
import springboot.java17.realworld.repository.ArticleRepository;
import springboot.java17.realworld.repository.TagRepository;
import springboot.java17.realworld.repository.UserRepository;

// 1. 실제 서버를 띄우고 테스트하는 통합 테스트 환경 설정
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ArticleControllerTest {

    @LocalServerPort
    protected int port;
    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected ArticleRepository articleRepository;

    @Autowired
    protected TagRepository tagRepository;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/articles";
    }

    @DisplayName("성공: 아티클 생성")
    @Test
    public void createArticleTest() {
        // Given
        NewArticleRequestDto createDto = NewArticleRequestDto.builder()
            .title("타이틀")
            .description("디스크립션")
            .body("바디")
            .tagList(List.of("태그1", "태그2"))
            .build();


        // When
        ResponseEntity<SingleArticleResponseDto> responseEntity = restTemplate.postForEntity(
            baseUrl(),
            createDto,
            SingleArticleResponseDto.class
        );


        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getArticle().getTitle()).isEqualTo("타이틀");
        assertThat(responseEntity.getBody().getArticle().getDescription()).isEqualTo("디스크립션");
        assertThat(responseEntity.getBody().getArticle().getTagList().get(0)).isEqualTo("태그1");
        assertThat(responseEntity.getBody().getArticle().getTagList().size()).isEqualTo(2);
    }

    @DisplayName("실패: 제목이 없을 경우 400 에러 반환")
    @Test
    public void createArticleTest_fail(){
        //Given
        NewArticleRequestDto createDtoWithoutTitle = NewArticleRequestDto.builder()
            .description("디스크립션")
            .body("바디")
            .build();

        // When
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
            baseUrl(),
            createDtoWithoutTitle,
            Object.class
        );

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}