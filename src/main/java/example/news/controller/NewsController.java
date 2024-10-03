package example.news.controller;


import example.news.dto.NewsDto;
import example.news.dto.NewsWithCommentsDto;

import example.news.dto.UserDto;
import example.news.filter.NewsFilter;
import example.news.security.AppUserPrincipal;
import example.news.security.SecurityManager;
import example.news.service.NewsService;
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
    @RequestMapping(path = "/news")
    @RequiredArgsConstructor

    public class NewsController {
        private final NewsService newsService;
    @Autowired
    private final SecurityManager securityManager;

        @GetMapping("/find/")
        @ResponseStatus(HttpStatus.OK)
        public List<NewsDto> findAllSpecification(
                @ModelAttribute NewsFilter filter,
                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                 @Positive @RequestParam(defaultValue = "10") Integer size) {

            PageRequest page = PageRequest.of(from / size, size);

            return newsService.filteredByCriteria(filter, page);
        }

        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        public List<NewsDto> findAll(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                     @Positive @RequestParam(defaultValue = "10") Integer size) {

            PageRequest page = PageRequest.of(from / size, size);
            return newsService.findAll(page);
        }

        @GetMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public NewsWithCommentsDto findById(@Positive @PathVariable("id") Long id) {
            return newsService.findById(id);
        }


   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)

   public NewsDto create(@Validated @RequestBody NewsDto newsDto) {
       return newsService.create(newsDto);
   }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)

    public NewsDto updateById(@Positive @PathVariable Long id,
                              @Validated @RequestBody NewsDto newsDto,
                              @AuthenticationPrincipal AppUserPrincipal userDetails) {
        Long ownerId = newsService.findById(id).getNewsDto().getUserId();
        securityManager.checkAuthor(id, userDetails);
        return newsService.update(newsDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    public void delete(@Positive @PathVariable Long id,
                       @AuthenticationPrincipal AppUserPrincipal userDetails) {
            Long ownerId = newsService.findById(id).getNewsDto().getUserId();
            securityManager.checkAccess(ownerId, userDetails);
        newsService.deleteById(id);
    }
    }


