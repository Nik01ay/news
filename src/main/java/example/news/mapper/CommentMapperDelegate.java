package example.news.mapper;

import example.news.dto.CommentDto;
import example.news.entity.CommentEntity;
import example.news.service.NewsService;
import example.news.service.UserService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class CommentMapperDelegate implements CommentMapper {
    /*

    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService  userService;
*/
    @Override
    public CommentEntity toCommentEntity(CommentDto commentDto) {

        return CommentEntity.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .createDate(commentDto.getCreateDate())
              /*  .news(newsService.findByIdOrNodFoundException(commentDto.getNewsId()))
                .user(userService.findEntityById(commentDto.getUser().getId()))

               */
                .build();
    }

    @Override
    public CommentDto toCommentDto(CommentEntity commentEntity) {
        CommentDto commentDto = CommentDto.builder()
                .id(commentEntity.getId())
                .content(commentEntity.getContent())
                .user(UserMapper.USER_MAPPER.toUserDto(commentEntity.getUser()))
                .createDate(commentEntity.getCreateDate()).
                build();
        return commentDto;
    }
}
