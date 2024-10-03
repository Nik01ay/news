package example.news.controller;

import example.news.dto.CommentDto;

import example.news.security.AppUserPrincipal;
import example.news.security.SecurityManager;
import example.news.service.CommentService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @Autowired
    private final SecurityManager securityManager;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)

    public List<CommentDto> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                    @Positive @RequestParam(defaultValue = "10") Integer size) {

        PageRequest page = PageRequest.of(from / size, size);
        return commentService.findAll(page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)

    public CommentDto findById(
            @Positive @PathVariable Long id) {
        return commentService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public CommentDto create(
            @Positive @RequestParam(name = "newsId") Long newsId,
            @RequestBody @Validated CommentDto commentDto,
             @AuthenticationPrincipal AppUserPrincipal userDetails
    )

    {

        return commentService.create(newsId,userDetails.getId(),  commentDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('COMMENT_UPDATE')")

    public CommentDto updateById(
            @RequestBody @Validated CommentDto commentDto,
            @AuthenticationPrincipal AppUserPrincipal userDetails) {
        //todo в чём смысл писать через АОП????
        securityManager.checkAccess(commentDto.getUser().getId(), userDetails);
        return commentService.update(commentDto.getId(), userDetails.getId(),commentDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('COMMENT_DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
           @Positive @RequestParam(name = "userId") Long userId,
           @Positive @PathVariable Long id,
           @AuthenticationPrincipal AppUserPrincipal userDetails) {
        securityManager.checkAccess(commentService.findById(id).getUser().getId(), userDetails);
        commentService.deleteById(id, userId);
    }

}


