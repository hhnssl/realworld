package springboot.java17.realworld.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springboot.java17.realworld.entity.ArticleEntity;
import springboot.java17.realworld.entity.UserEntity;
import springboot.java17.realworld.repository.ArticleRepository;
import springboot.java17.realworld.repository.UserRepository;

@SpringBootTest // 테스트용 앱 컨택스트 생성
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성
class ArticleControllerTestMock {

    UserEntity testUSer;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    public void cleanUp() {
        articleRepository.deleteAll();
    }

    @BeforeEach
    public void beforeEach(){
        testUSer = UserEntity.builder()
            .email("user1@gmail.com")
            .username("user1")
            .password("password")
            .build();

        userRepository.save(testUSer);
    }

    @DisplayName("아티클 조회에 성공한다.")
    @Test
    public void getAllArticles() throws Exception {
        // given
        final String url = "/api/articles";
        ArticleEntity savedArticle = articleRepository.save(ArticleEntity.builder()
            .user(testUSer)
            .title("title")
            .slug("slug")
            .description("desciption")
            .body("body")
            .build());

        // when
        final ResultActions result = mockMvc.perform(get(url)
            .accept(MediaType.APPLICATION_JSON));


        // then
        result
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.articles[0].title").value(savedArticle.getTitle()))
            .andExpect(jsonPath("$.articles[0].body").value(savedArticle.getBody()));
    }

}