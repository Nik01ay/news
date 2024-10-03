package example.news.controller;

import example.news.dto.CommentDto;
import example.news.dto.UserDto;
import example.news.security.AppUserPrincipal;
import example.news.security.SecurityManager;
import example.news.service.UserService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final SecurityManager securityManager;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<UserDto> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                 @Positive @RequestParam(defaultValue = "10") Integer size) {

        PageRequest page = PageRequest.of(from / size, size);
        return userService.findAll(page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findById(
            @Positive @PathVariable Long id,
            @AuthenticationPrincipal AppUserPrincipal userDetails) {

        securityManager.checkAccess(id, userDetails);

        return userService.findById(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public UserDto create(@Validated @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)

    public UserDto updateById(@Positive @PathVariable Long id,
                              @Validated @RequestBody UserDto userDto,
                              @AuthenticationPrincipal AppUserPrincipal userDetails) {
        securityManager.checkAccess(id, userDetails);
        return userService.update(userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    public void deleteById(@Positive @PathVariable Long id,
                           @AuthenticationPrincipal AppUserPrincipal userDetails) {
        securityManager.checkAccess(id, userDetails);
        userService.deleteById(id);
    }


}
