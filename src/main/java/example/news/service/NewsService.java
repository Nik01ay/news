package example.news.service;

import example.news.aop.Loggable;
import example.news.aop.SecureAccess;
import example.news.dto.NewsDto;
import example.news.dto.NewsWithCommentsDto;
import example.news.entity.NewsEntity;
import example.news.error.exceptions.ObjectNotFoundException;
import example.news.filter.NewsFilter;
import example.news.mapper.NewsMapper;
import example.news.repository.NewsRepository;
import example.news.specification.NewsSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;
    public Optional<List<NewsEntity>> getByUserId(Long id) {
        System.out.println("servise");
        List<NewsEntity> newsList = newsRepository.findByUserId(id);

        return Optional.ofNullable(newsList);
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

    @SecureAccess
    public NewsDto update(Long id, Long userId, NewsDto newsDto) {
        NewsEntity ne = findByIdOrNodFoundException(newsDto.getId());
            ne.setContent(newsDto.getContent());
            ne.setTitle(newsDto.getTitle());
            newsRepository.save(ne);
        return NewsMapper.NEWS_MAPPER.toNewsDto(ne);
    }
@SecureAccess
    public void deleteById(Long id, Long userId) {
           NewsEntity ne = findByIdOrNodFoundException(id);
           newsRepository.deleteById(ne.getId());

    }
@Loggable
    public List<NewsDto> filteredByCriteria(NewsFilter filter, PageRequest page) {
        return newsRepository.findAll(NewsSpecification.byNewsNameAndOwnerIdFilter(filter), page).stream()
                .map(NewsMapper.NEWS_MAPPER::toNewsDto)
                .collect(Collectors.toList());
    }
}
