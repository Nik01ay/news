package example.news.mapper;

import example.news.dto.UserDto;
import example.news.entity.*;
import example.news.repository.RoleRepository;
import example.news.service.CommentService;
import example.news.service.NewsService;
import example.news.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class UserMapperDelegate implements UserMapper {
    @Autowired
    private NewsService newsService;
    @Autowired
    private CommentService commentService;

    @Autowired
    private RoleService roleService;


    @Override
    public UserEntity toUserEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setName(userDto.getName());

        List<NewsEntity> newsEntityList;
        if (newsService != null) {
            newsEntityList = newsService.getByUserId(userDto.getId()).orElse(new ArrayList<>());
        } else newsEntityList = new ArrayList<>();
        userEntity.setNewsEntityList(newsEntityList);


        List<CommentEntity> commentEntityList;
        if (commentService != null) {
            commentEntityList = commentService.getByUserId(userDto.getId()).orElse(new ArrayList<>());
        } else commentEntityList = new ArrayList<>();
        userEntity.setCommentEntityList(commentEntityList);

        userEntity.setRoles(roleService.findByNameSet(userDto.getRoles()));

        return userEntity;
    }

    @Override
    public UserDto toUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        userDto.setRoles(userEntity.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));

        userDto.setPermission(userEntity.getRoles().stream().flatMap(
                role -> role.getPermissions().stream()
                        .map(Permission::getName))
                        .collect(Collectors.toSet()));

        return userDto;
    }

}
