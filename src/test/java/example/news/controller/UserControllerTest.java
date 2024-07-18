package example.news.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.news.dto.UserDto;
import example.news.service.UserService;
//import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;

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
//@Before
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
    }


    @Test
   // @SneakyThrows
    void findAll() throws Exception {
        setUp();
           mockMvc.perform(get("http://localhost:8080/users"))
                    .andExpect(status().is2xxSuccessful())

                    .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane"))
                ;



    }

    @Test
    void findById() throws Exception {
        setUp();
        mockMvc.perform(get("http://localhost:8080/users/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("John"))
                ;

    }

    @Test
    void create() throws Exception {
        UserDto newUser = new UserDto();
        newUser.setName("Mike");


        mockMvc.perform(post("http://localhost:8080/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Mike"));

    }

    @Test
    void updateById() throws Exception {
        setUp();
        UserDto newUser = new UserDto();
        newUser.setName("Mike");
        newUser.setId(1L);


        mockMvc.perform(put("http://localhost:8080/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Mike"));

    }



    @Test
    void deleteById() throws Exception {
        setUp();
        mockMvc.perform(delete("http://localhost:8080/users/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("http://localhost:8080/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].name").value("Jane"));

    }
}