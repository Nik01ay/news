package example.news.service;

import example.news.dto.CommentDto;
import example.news.entity.CommentEntity;
import example.news.entity.NewsEntity;
import example.news.error.exceptions.ObjectNotFoundException;
import example.news.mapper.CommentMapper;
import example.news.repository.CommentRepository;
import example.news.repository.NewsRepository;
import example.news.repository.UserRepository;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    NewsRepository newsRepository;

    public List<CommentEntity> getByNewsId(Long id) {
        return commentRepository.findAllByNewsId(id);
    }

    public List<CommentEntity> getByUserId(Long id) {
        return commentRepository.findAllByUserId(id);
    }

    public List<CommentDto> findAll(PageRequest page) {
        return commentRepository.findAll().stream().map(CommentMapper.COMMENT_MAPPER::toCommentDto).collect(Collectors.toList());
    }

    public CommentDto findById(Long id) {
        return CommentMapper.COMMENT_MAPPER.toCommentDto(findByIdOrNotFoundThrow(id));
    }

    public CommentEntity findByIdOrNotFoundThrow(Long id) {
        return commentRepository.findById(id).orElseThrow(()
                        -> {
                    log.warn("Comment id {} not found", id);
                    throw new ObjectNotFoundException("Comment not found");
                }
        );


    }

    public CommentDto create(Long newsId, Long userId, CommentDto commentDto) {
/// TODO: 19.01.2024  переделать на сервис
        CommentEntity comment = CommentMapper.COMMENT_MAPPER.toCommentEntity(commentDto);
        comment.setNews(newsRepository.findById(newsId).get());
        comment.setUser(userRepository.findById(userId).get());
        CommentEntity savedComment = commentRepository.save(comment);
        return CommentMapper.COMMENT_MAPPER.toCommentDto(savedComment);
    }


    public CommentDto update(Long newsId, Long userId, CommentDto commentDto) {

        CommentEntity comment = findByIdOrNotFoundThrow(commentDto.getId());
        if (verificationUserIdAuthor(comment.getId(), userId)) {
            comment.setContent(commentDto.getContent());
            comment.setCreateDate(Timestamp.from(Instant.now()));
            NewsEntity news = newsRepository.findById(newsId).get();
            comment.setNews(news);
        }
        CommentEntity savedComment = commentRepository.save(comment);
        return CommentMapper.COMMENT_MAPPER.toCommentDto(savedComment);
    }

    public void deleteById(Long id, Long userId) {
        CommentEntity comment = findByIdOrNotFoundThrow(id);
        if (verificationUserIdAuthor(comment.getId(), userId)) {
            commentRepository.deleteById(id);
        }
    }

    private boolean verificationUserIdAuthor(Long commentId, Long userId) {
        if (!(commentRepository.findById(commentId).get().getUser().getId() == userId)){
            log.warn("access error not rights edit comments");
            throw new ObjectNotFoundException("access error. rights don't allow for edit comment");
        } else
        return true;
    }
}
