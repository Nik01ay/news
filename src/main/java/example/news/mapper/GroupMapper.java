package example.news.mapper;

import example.news.dto.GroupDto;
import example.news.entity.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {
    GroupMapper GROUP_MAPPER = Mappers.getMapper(GroupMapper.class);
    GroupEntity toGroupEntity(GroupDto groupDto);
    GroupDto toGroupDto(GroupEntity groupEntity);
}
