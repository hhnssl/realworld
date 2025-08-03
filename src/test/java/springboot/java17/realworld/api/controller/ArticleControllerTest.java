package springboot.java17.realworld.api.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.http.ResponseEntity;
import springboot.java17.realworld.api.dto.articleDtos.request.NewArticleRequestDto;
import springboot.java17.realworld.api.dto.articleDtos.response.SingleArticleResponseDto;
import springboot.java17.realworld.api.dto.userDtos.request.NewUserRequestDto;
import springboot.java17.realworld.api.dto.userDtos.response.UserResponseDto;
import springboot.java17.realworld.repository.ArticleRepository;
import springboot.java17.realworld.service.UserService;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArticleControllerTest {


    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected ArticleRepository articleRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    private String jwtToken;


    private String baseUrl() {
        return "http://localhost:" + port + "/api/articles";
    }

    @BeforeEach
    void setUp() {
        NewUserRequestDto request = new NewUserRequestDto();
        request.setUsername("testuser");
        request.setEmail("testuset@gmail.com");
        request.setPassword("testpw");

        UserResponseDto response = userService.register(request);
        this.jwtToken = response.getUser().getToken();


    }

    @DisplayName("성공 - 게시글 작성")
    @Test
    public void createArticleTest() {
        // Given
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
        long beforeCount = articleRepository.count();

        NewArticleRequestDto createDto = NewArticleRequestDto.builder()
            .title("테스트 코드 작성하기")
            .description("테스트 코드를 작성해봅시다.")
            .body("테스트 코드를 작성하는 방법은요...")
            .tagList(List.of("junit5", "테스트코드"))
            .build();


        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.jwtToken);

        HttpEntity<NewArticleRequestDto> request = new HttpEntity<>(createDto, headers);

        // When
        ResponseEntity<SingleArticleResponseDto> responseEntity = restTemplate.exchange(
            baseUrl(),
            HttpMethod.POST,
            request,
            SingleArticleResponseDto.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getArticle().getTitle()).isEqualTo("테스트 코드 작성하기");
        assertThat(responseEntity.getBody().getArticle().getDescription()).isEqualTo("테스트 코드를 작성해봅시다.");
        assertThat(responseEntity.getBody().getArticle().getTagList().size()).isEqualTo(2);
        assertThat(articleRepository.count()).isEqualTo(beforeCount + 1);
    }

}