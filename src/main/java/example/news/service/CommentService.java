package example.news.service;

import example.news.dto.CommentDto;
import example.news.dto.UserDto;
import example.news.entity.CommentEntity;
import example.news.mapper.CommentMapper;
import example.news.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    public List<CommentEntity> getByNewsId(Long id) {
        return commentRepository.findAllByNewsId(id);
    }

    public List<CommentEntity> getByUserId(Long id) {
        return commentRepository.findAllByUserId(id);
    }

    public List<CommentDto> findAll(PageRequest page) {
        return commentRepository.findAll().stream().map(CommentMapper.COMMENT_MAPPER::toCommentDto).collect(Collectors.toList());
    }
}
