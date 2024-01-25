package example.news.mapper;

import example.news.dto.NewsDto;
import example.news.dto.NewsWithCommentsDto;
import example.news.entity.NewsEntity;
import example.news.service.CommentService;
import example.news.service.GroupService;
import example.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

public abstract class NewsMapperDelegate implements NewsMapper {
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    @Autowired
    private CommentService commentService;


    @Override
    public NewsEntity toNewsEntity(NewsDto newsDto) {
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setContent(newsDto.getContent());
        newsEntity.setId(newsDto.getId());
        newsEntity.setTitle(newsDto.getTitle());
        newsEntity.setCreateDate(newsDto.getCreateDate());
        newsEntity.setUser(userService.findByIdOrNodFoundException(newsDto.getUserId()));
        newsEntity.setGroup(groupService.findByIdOrNodFoundException(newsDto.getGroupId()));
        newsEntity.setCommentEntityList(commentService.getByNewsId(newsDto.getId()));
        return newsEntity;
    }

    @Override
    public NewsDto toNewsDto(NewsEntity newsEntity) {
        NewsDto newsDto = new NewsDto();
        newsDto.setContent(newsEntity.getContent());
        newsDto.setId(newsEntity.getId());
        newsDto.setTitle(newsEntity.getTitle());
        newsDto.setCreateDate(newsEntity.getCreateDate());
        newsDto.setUserId(newsEntity.getUser().getId());
        newsDto.setGroupId(newsEntity.getGroup().getId());
        newsDto.setUserId(newsEntity.getUser().getId());
        newsDto.setGroupTitle(newsEntity.getGroup().getTitle());
        newsDto.setCountComments(newsEntity.getCommentEntityList().size());
        return newsDto;
    }

    @Override
    public NewsWithCommentsDto toNewsWithCommentsDto(NewsEntity newsEntity) {
        NewsWithCommentsDto ncDto = new NewsWithCommentsDto();
        ncDto.setNewsDto(toNewsDto(newsEntity));
        ncDto.setCommentDtoList(newsEntity.getCommentEntityList().stream().map(CommentMapper.COMMENT_MAPPER::toCommentDto).collect(Collectors.toList()));
        return ncDto;
    }
}
