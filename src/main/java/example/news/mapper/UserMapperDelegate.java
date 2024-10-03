package example.news.mapper;

import example.news.dto.UserDto;
import example.news.entity.*;
import example.news.repository.RoleRepository;
import example.news.service.CommentService;
import example.news.service.NewsService;
import example.news.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring")

public abstract class UserMapperDelegate implements UserMapper {

    // todo  не получилось добавить сервисы. наверно их тут не должно быть.
    /*@Autowired
    private NewsService newsService;
    @Autowired
    private CommentService commentService;

    @Autowired
    private RoleService roleService;
*/


    @Override
    public UserEntity toUserEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setName(userDto.getName());
        userEntity.setPassword(userDto.getPassword());

        /*List<NewsEntity> newsEntityList;
        if (newsService != null) {
            newsEntityList = newsService.getByUserId(userDto.getId()).orElse(new ArrayList<>());
        } else newsEntityList = new ArrayList<>();
        */


        /*List<CommentEntity> commentEntityList;
        if (commentService != null) {
            commentEntityList = commentService.getByUserId(userDto.getId()).orElse(new ArrayList<>());
        } else commentEntityList = new ArrayList<>();


        userEntity.setCommentEntityList(commentEntityList);
*/
        //if (roleService !=null) {
            /*userEntity.setRoles(roleService.findByNameSet(userDto.getRoleNames())
                    .orElse(roleService.addRoleSet(userDto.getRoleNames())));
*/


        //}



        return userEntity;
    }

    @Override
    public UserDto toUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setName(userEntity.getName());
        userDto.setPassword(userEntity.getPassword());
        Set<String> roleNames =   userEntity.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        userDto.setRoleNames( roleNames);


/*
        userDto.setPermission(userEntity.getRoles().stream().flatMap(
                role -> role.getPermissions().stream()
                        .map(Permission::getName))
                        .collect(Collectors.toSet()));

 */
        userDto.setPermission(new HashSet<>());
        System.out.println("toUserDTO");
        System.out.println(userDto);

        return userDto;
    }


}
