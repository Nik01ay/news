package example.news.mapper;

import example.news.dto.UserDto;
import example.news.entity.UserEntity;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
@DecoratedWith(UserMapperDelegate.class)
@Mapper (componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);
    UserDto toUserDto (UserEntity userEntity);
    UserEntity toUserEntity (UserDto userDto);
}
