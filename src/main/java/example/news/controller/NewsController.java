package example.news.controller;


import example.news.dto.NewsDto;
import example.news.dto.NewsWithCommentsDto;

import example.news.filter.NewsFilter;
import example.news.service.NewsService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Validated
    @RestController
    @RequestMapping(path = "/news")
    @RequiredArgsConstructor

    public class NewsController {
        private final NewsService newsService;


        @GetMapping("/find/")
        @ResponseStatus(HttpStatus.OK)
        @PreAuthorize("hasAnyAuthority('NEWS_READ')")
        public List<NewsDto> findAllSpecification(
                @ModelAttribute NewsFilter filter,
                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                 @Positive @RequestParam(defaultValue = "10") Integer size) {

            PageRequest page = PageRequest.of(from / size, size);

            return newsService.filteredByCriteria(filter, page);
        }

        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        @PreAuthorize("hasAnyAuthority('NEWS_READ')")
        public List<NewsDto> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                     @Positive @RequestParam(defaultValue = "10") Integer size) {

            PageRequest page = PageRequest.of(from / size, size);
            return newsService.findAll(page);
        }

        @GetMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        @PreAuthorize("hasAnyAuthority('NEWS_READ')")
        public NewsWithCommentsDto findById(@Positive @PathVariable("id") Long id) {
            return newsService.findById(id);
        }

    }
