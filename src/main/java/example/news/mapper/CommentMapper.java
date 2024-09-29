package example.news.mapper;

import example.news.dto.CommentDto;
import example.news.dto.GroupDto;
import example.news.entity.CommentEntity;
import example.news.entity.GroupEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface CommentMapper {
    CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);


    CommentEntity toCommentEntity(CommentDto commentDto);

    CommentDto toCommentDto(CommentEntity commentEntity);
}
