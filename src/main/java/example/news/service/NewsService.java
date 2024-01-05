package example.news.service;

import example.news.entity.NewsEntity;
import example.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NewsService {
    @Autowired
    private NewsRepository newsRepository;
    public List<NewsEntity> getByUserId(Long id) {
        return newsRepository.findByUserId(id);
    }
}
