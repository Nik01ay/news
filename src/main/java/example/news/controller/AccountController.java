package example.news.controller;


import example.news.dto.UserDto;
import example.news.mapper.UserMapper;
import example.news.mapper.UserMapperImpl;
import example.news.service.RoleService;
import example.news.service.UserService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.IllegalQueryOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
       @Autowired
    private final UserService service;
@Autowired
private final RoleService roleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto request) throws IllegalQueryOperationException {
        return service.create(request);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                 @Positive @RequestParam(defaultValue = "10") Integer size) {
        System.out.println("get_Account");
        PageRequest page = PageRequest.of(from / size, size);
        return service.findAll(page);


    }
}