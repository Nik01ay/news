package example.news.controller;


import example.news.dto.NewsDto;
import example.news.dto.NewsWithCommentsDto;

import example.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;




    @RestController
    @RequestMapping(path = "/news")
    @RequiredArgsConstructor
    public class NewsController {
        private final NewsService newsService;

        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        public List<NewsDto> findAll(@RequestParam(defaultValue = "0") Integer from,
                                     @RequestParam(defaultValue = "10") Integer size) {

            PageRequest page = PageRequest.of(from / size, size);
            return newsService.findAll(page);
        }

        @GetMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public NewsWithCommentsDto findById(@PathVariable("id") Long id) {
            return newsService.findById(id);
        }

    }
