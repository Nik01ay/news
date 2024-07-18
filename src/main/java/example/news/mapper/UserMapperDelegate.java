package example.news.mapper;

import example.news.dto.UserDto;
import example.news.entity.CommentEntity;
import example.news.entity.NewsEntity;
import example.news.entity.UserEntity;
import example.news.service.CommentService;
import example.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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

        List<NewsEntity> newsEntityList;
        if (newsService != null) {
             newsEntityList = newsService.getByUserId(userDto.getId()).orElse(new ArrayList<>());
        }   else newsEntityList =new ArrayList<>() ;
        userEntity.setNewsEntityList(newsEntityList);


        List<CommentEntity> commentEntityList;
        if (commentService !=null) {
            commentEntityList = commentService.getByUserId(userDto.getId()).orElse(new ArrayList<>());
        }
        else commentEntityList = new ArrayList<>();
        userEntity.setCommentEntityList(commentEntityList);

        //userEntity.setNewsEntityList(newsService.getByUserId(userDto.getId()).orElse(new ArrayList<>()));
        //userEntity.setCommentEntityList(commentService.getByUserId(userDto.getId()).orElse(new ArrayList<>()));

        return userEntity;
    }
    @Override
    public UserDto toUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        return userDto;
    }

}
