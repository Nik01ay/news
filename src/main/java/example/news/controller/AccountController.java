package example.news.controller;


import example.news.dto.UserDto;
import example.news.mapper.UserMapper;
import example.news.mapper.UserMapperImpl;
import example.news.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.IllegalQueryOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto request) throws IllegalQueryOperationException {
        return service.create( request);

    }
}
