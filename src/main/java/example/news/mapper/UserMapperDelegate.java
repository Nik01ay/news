package example.news.mapper;

import example.news.dto.UserDto;
import example.news.entity.UserEntity;
import example.news.service.CommentService;
import example.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class UserMapperDelegate implements UserMapper{
    @Autowired
    private NewsService newsService;
    @Autowired
    private CommentService commentService;

    @Override
    public UserEntity toUserEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setName(userDto.getName());
        userEntity.setNewsEntityList(newsService.getByUserId(userDto.getId()));
        userEntity.setCommentEntityList(commentService.getByUserId(userDto.getId()));
        return userEntity;
    }
}
