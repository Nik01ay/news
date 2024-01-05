package example.news.service;

import example.news.dto.UserDto;
import example.news.entity.UserEntity;
import example.news.mapper.UserMapper;
import example.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public UserEntity findById(Long id){
        UserEntity userEntity = userRepository.findById(id).orElse(new UserEntity());
        return userEntity;
    };

    public UserDto create(UserDto userDto){
        UserEntity userEntity = UserMapper.USER_MAPPER.toUserEntity(userDto);
        userRepository.save(userEntity);
        log.info("UserService -> User created");
        return UserMapper.USER_MAPPER.toUserDto(userEntity);
    };

    public UserDto update(UserDto userDto){
        UserEntity ue = userRepository.save(UserMapper.USER_MAPPER.toUserEntity(userDto));
        return UserMapper.USER_MAPPER.toUserDto(ue);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
