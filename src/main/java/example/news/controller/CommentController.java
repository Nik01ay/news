package example.news.controller;

import example.news.dto.CommentDto;

import example.news.service.CommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> findAll(@RequestParam(defaultValue = "0") Integer from,
                                    @RequestParam(defaultValue = "10") Integer size) {

        PageRequest page = PageRequest.of(from / size, size);
        return commentService.findAll(page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto findById(
            @PathVariable Long id) {
        return commentService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(
            @RequestParam(name = "newsId") Long newsId,
            @RequestParam(name = "userId") Long userId,
            @RequestBody CommentDto commentDto) {
        return commentService.create(newsId, userId, commentDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateById(
            @RequestParam(name = "userId") Long userId,
            @RequestBody CommentDto commentDto) {
        return commentService.update(commentDto.getId(), userId,commentDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @RequestParam(name = "userId") Long userId,
            @PathVariable Long id) {
        commentService.deleteById(id, userId);
    }

}


