package example.news.mapper;

import example.news.dto.NewsDto;
import example.news.dto.NewsWithCommentsDto;
import example.news.entity.NewsEntity;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {
    NewsMapper NEWS_MAPPER = Mappers.getMapper(NewsMapper.class);

    NewsDto toNewsDto(NewsEntity newsEntity);

    NewsWithCommentsDto toNewsWithCommentsDto (NewsEntity newsEntity);
    NewsEntity toNewsEntity (NewsDto newsDto);

}
