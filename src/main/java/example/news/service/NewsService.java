package example.news.service;

import example.news.dto.NewsDto;
import example.news.dto.NewsWithCommentsDto;
import example.news.entity.NewsEntity;
import example.news.mapper.NewsMapper;
import example.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return newsRepository.findById(id).map(NewsMapper.NEWS_MAPPER::toNewsWithCommentsDto).get();
    }
}
