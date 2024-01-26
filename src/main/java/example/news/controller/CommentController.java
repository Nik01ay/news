package example.news.controller;

import example.news.dto.CommentDto;

import example.news.service.CommentService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

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
            @Positive @RequestParam(name = "userId") Long userId,
            @RequestBody @Validated CommentDto commentDto) {
        return commentService.create(newsId, userId, commentDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateById(
            @Positive @RequestParam(name = "userId") Long userId,
            @RequestBody @Validated CommentDto commentDto) {
        return commentService.update(commentDto.getId(), userId,commentDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
           @Positive @RequestParam(name = "userId") Long userId,
           @Positive @PathVariable Long id) {
        commentService.deleteById(id, userId);
    }

}


