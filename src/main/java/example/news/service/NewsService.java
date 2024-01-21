package example.news.service;

import example.news.dto.NewsDto;
import example.news.dto.NewsWithCommentsDto;
import example.news.dto.UserDto;
import example.news.entity.NewsEntity;
import example.news.entity.UserEntity;
import example.news.error.exceptions.ObjectNotFoundException;
import example.news.mapper.NewsMapper;
import example.news.mapper.UserMapper;
import example.news.repository.NewsRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;
    public List<NewsEntity> getByUserId(Long id) {
        return newsRepository.findByAuthorId(id);
    }

    public List<NewsDto> findAll(PageRequest page) {
        return newsRepository.findAll().stream().map(NewsMapper.NEWS_MAPPER::toNewsDto).collect(Collectors.toList());
    }

    public NewsWithCommentsDto findById(Long id) {
        NewsEntity ne = findByIdOrNodFoundException(id);
        return NewsMapper.NEWS_MAPPER.toNewsWithCommentsDto(ne);
    }

    public NewsEntity findByIdOrNodFoundException(Long id) {

        return newsRepository.findById(id).orElseThrow(()
                        -> {
                    log.warn("News id {} not found", id);
                    throw new ObjectNotFoundException("News not found");
                }
        );
    }

    public NewsDto create(NewsDto newsDto) {
        NewsEntity newsEntity = NewsMapper.NEWS_MAPPER.toNewsEntity(newsDto);
        newsRepository.save(newsEntity);
        log.info("newsService -> News created");
        return NewsMapper.NEWS_MAPPER.toNewsDto(newsEntity);
    }


    public NewsDto update(NewsDto newsDto, Long userId) {
        NewsEntity ne = findByIdOrNodFoundException(newsDto.getId());
        if (verificationUserIdAuthor(newsDto.getAuthorId(), userId)) {
            ne.setContent(newsDto.getContent());
            ne.setTitle(newsDto.getTitle());
            newsRepository.save(ne);
        }
        return NewsMapper.NEWS_MAPPER.toNewsDto(ne);
    }

    public void deleteById(Long id, Long userId) {
       if (verificationUserIdAuthor(id, userId)) {
           NewsEntity ne = findByIdOrNodFoundException(id);
           newsRepository.deleteById(ne.getId());
       }
    }

    private boolean verificationUserIdAuthor(Long newsId, Long userId) {
        if (!(newsRepository.findById(newsId).get().getAuthor().getId() == userId)){
            log.warn("edit news access error");
            throw new ObjectNotFoundException("access error. rights don't allow for edit news");
        } else
            return true;
    }


}
