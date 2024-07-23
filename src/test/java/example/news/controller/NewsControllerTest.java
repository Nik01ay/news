package example.news.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.news.dto.GroupDto;
import example.news.dto.NewsDto;
import example.news.dto.UserDto;
import example.news.service.GroupService;
import example.news.service.NewsService;
import example.news.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NewsService newsService;
    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ObjectMapper objectMapper;

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.generate-ddl", ()-> true);

    }
    @BeforeEach
    public void setUp() {
        // Инициализация данных для тестирования
        UserDto user1 = new UserDto();
        user1.setId(1L);
        user1.setName("John");
        userService.create(user1);

        UserDto user2 = new UserDto();
        user2.setId(2L);
        user2.setName("Jane");
        userService.create(user2);

        GroupDto groupDto = new GroupDto();
        groupDto.setId(1L);
        groupDto.setTitle("group1");
        groupService.create(groupDto);

        GroupDto groupDto2 = new GroupDto();
        groupDto2.setId(2L);
        groupDto2.setTitle("group2");
        groupService.create(groupDto2);

        NewsDto newsDto = new NewsDto();
        newsDto.setId(1L);
        newsDto.setContent("Content news1");
        newsDto.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        newsDto.setTitle("Title news1");
        newsDto.setCountComments(0);
        newsDto.setUserId(1L);
        newsDto.setUserName(userService.findById(1L).getName());
        newsDto.setGroupId(1L);
        newsDto.setGroupTitle("group1");


        newsService.create(newsDto);

        NewsDto newsDto2 = new NewsDto();
        newsDto2.setId(2L);
        newsDto2.setContent("Content news2");
        newsDto2.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        newsDto2.setTitle("Title news2");
        newsDto2.setCountComments(0);
        newsDto2.setUserId(2L);
        newsDto2.setUserName(userService.findById(2L).getName());
        newsDto2.setGroupId(2L);
        newsDto2.setGroupTitle("group2");
        newsService.create(newsDto2);

        NewsDto newsDto3 = new NewsDto();
        newsDto.setId(3L);
        newsDto.setContent("Content news3");
        newsDto.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        newsDto.setTitle("Title news3");
        newsDto.setCountComments(0);
        newsDto.setUserId(2L);
        newsDto.setUserName(userService.findById(2L).getName());
        newsService.create(newsDto3);

    }

    @Test
    void findAllSpecification() throws Exception {


    }

    @Test
    void findAll() throws  Exception {
        mockMvc.perform(get("http://localhost:8080/news"))
                .andExpect(status().is2xxSuccessful())

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].content").value("Content news1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].content").value("Content news2"))        ;



    }

    @Test
    void findById() {
    }
}