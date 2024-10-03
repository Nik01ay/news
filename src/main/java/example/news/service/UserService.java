package example.news.service;

import example.news.dto.InterfaceDto;
import example.news.dto.UserDto;
import example.news.entity.UserEntity;
import example.news.error.exceptions.ObjectNotFoundException;
import example.news.mapper.UserMapper;
import example.news.repository.RoleRepository;
import example.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor

public class UserService {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleService roleService;


    public List<UserDto> findAll(Pageable page) {
        List<UserEntity> userEntityList = userRepository.findAll(page).getContent();

        return userEntityList.stream().map(
                UserMapper.USER_MAPPER::toUserDto).toList();
    }

    public UserDto findById(Long id) {
            return UserMapper.USER_MAPPER.toUserDto(findByIdOrNodFoundException(id));

    }

    public UserEntity findEntityById(Long id) {
        return findByIdOrNodFoundException(id);
    }

    public UserEntity findByIdOrNodFoundException(Long id) {

        return userRepository.findById(id).orElseThrow(()
                        -> {
                    log.warn("User id {} not found", id);
                    throw new ObjectNotFoundException("User not found");
                }
        );

    }

    public UserDto create(UserDto userDto) {
        UserEntity userEntity = UserMapper.USER_MAPPER.toUserEntity(userDto);

        userEntity.setRoles(roleService.addRoleSet(userDto.getRoleNames()));

        userRepository.save(userEntity);
        log.info("UserService -> User created");
        return UserMapper.USER_MAPPER.toUserDto(userEntity);
    }

    ;

    public UserDto update(UserDto userDto) {

        UserEntity ue = findByIdOrNodFoundException(userDto.getId());
        ue.setName(userDto.getName());
        userRepository.save(ue);
        return UserMapper.USER_MAPPER.toUserDto(ue);
    }

    public void deleteById(Long id) {
        UserEntity ue = findByIdOrNodFoundException(id);
        userRepository.deleteById(ue.getId());
    }

    @Transactional(readOnly = true)
    public UserDto findByName(String username) {



        UserEntity user = userRepository.findFirstByName(username);
        user.setPassword( passwordEncoder.encode(user.getPassword()));
        System.out.println(user);



        return  UserMapper.USER_MAPPER.toUserDto(user);
    }
}
