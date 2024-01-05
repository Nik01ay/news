package example.news.service;

import example.news.entity.CommentEntity;
import example.news.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    public List<CommentEntity> getByNewsId(Long id) {
        return commentRepository.findAllByNews(id);
    }

    public List<CommentEntity> getByUserId(Long id) {
        return commentRepository.findAllByUser(id);
    }
}
