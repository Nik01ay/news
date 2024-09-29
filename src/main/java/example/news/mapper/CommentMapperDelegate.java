package example.news.mapper;

import example.news.dto.CommentDto;
import example.news.entity.CommentEntity;

public abstract class CommentMapperDelegate implements CommentMapper {

    @Override
    public CommentEntity toCommentEntity(CommentDto commentDto) {

        CommentEntity commentEntity = CommentEntity.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .createDate(commentDto.getCreateDate())
                .news(commentDto.getNews())
                .user(UserMapper.USER_MAPPER.toUserEntity(commentDto.getUser()))
                .build();
        return commentEntity;
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
