package example.news.mapper;

import example.news.dto.NewsDto;
import example.news.entity.NewsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NewsMapper {
    NewsMapper NEWS_MAPPER = Mappers.getMapper(NewsMapper.class);

    NewsDto toNewsDto(NewsEntity newsEntity);
    NewsEntity toNewsEntity (NewsDto newsDto);

}
