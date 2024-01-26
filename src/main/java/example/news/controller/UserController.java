package example.news.controller;

import example.news.dto.CommentDto;
import example.news.dto.UserDto;
import example.news.service.UserService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                 @Positive @RequestParam(defaultValue = "10") Integer size) {

        PageRequest page = PageRequest.of(from / size, size);
        return userService.findAll(page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findById(
            @Positive @PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create( @Validated @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateById( @Validated @RequestBody UserDto userDto) {
        return userService.update(userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Positive @PathVariable Long id) {
        userService.deleteById(id);
    }


}
