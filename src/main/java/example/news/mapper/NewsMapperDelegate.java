package example.news.mapper;

import example.news.dto.NewsDto;
import example.news.entity.NewsEntity;
import example.news.service.CommentService;
import example.news.service.GroupService;
import example.news.service.NewsService;
import example.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NewsMapperDelegate implements  NewsMapper{
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
        newsEntity.setAuthor(userService.findById(newsDto.getAuthorId()));
        newsEntity.setGroup(groupService.getByNewsId(newsDto.getIdTitle()));
        newsEntity.setCommentEntityList(commentService.getByNewsId(newsDto.getId()));
        return newsEntity;
    }
}
