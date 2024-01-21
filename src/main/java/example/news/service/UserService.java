package example.news.service;

import example.news.dto.InterfaceDto;
import example.news.dto.UserDto;
import example.news.entity.UserEntity;
import example.news.error.exceptions.ObjectNotFoundException;
import example.news.mapper.UserMapper;
import example.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> findAll(Pageable page) {
        List<UserEntity> userEntityList = userRepository.findAll(page).getContent();

        return userEntityList.stream().map(
                UserMapper.USER_MAPPER::toUserDto).toList();
    }

    public UserDto findById(Long id) {
        return UserMapper.USER_MAPPER.toUserDto(findByIdOrNodFoundException(id));
    }

    ;

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


}
