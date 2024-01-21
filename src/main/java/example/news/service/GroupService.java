package example.news.service;

import example.news.dto.GroupDto;
import example.news.dto.NewsDto;
import example.news.entity.GroupEntity;
import example.news.entity.NewsEntity;
import example.news.error.exceptions.ObjectNotFoundException;
import example.news.mapper.GroupMapper;
import example.news.mapper.NewsMapper;
import example.news.repository.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepository;

    public GroupEntity getByNewsId(Long id) {
        return groupRepository.getReferenceById(id);
    }

    public List<GroupDto> findAll(PageRequest page) {
        return groupRepository.findAll().stream().map(GroupMapper.GROUP_MAPPER::toGroupDto).collect(Collectors.toList());
    }

    public GroupDto getById(Long id) {
        GroupEntity ge = findByIdOrNodFoundException(id);
        return GroupMapper.GROUP_MAPPER.toGroupDto(ge);
    }

    public GroupDto create(GroupDto groupDto) {
        GroupEntity groupEntity = GroupMapper.GROUP_MAPPER.toGroupEntity(groupDto);
        groupRepository.save(groupEntity);
        log.info("groupService -> group created");
        return GroupMapper.GROUP_MAPPER.toGroupDto(groupEntity);
    }


    public GroupDto update(GroupDto groupDto) {
        GroupEntity ge = findByIdOrNodFoundException(groupDto.getId());
        ge.setTitle(groupDto.getTitle());
        groupRepository.save(ge);
        return GroupMapper.GROUP_MAPPER.toGroupDto(ge);
    }

    public void deleteById(Long id) {
        GroupEntity ge = findByIdOrNodFoundException(id);
        groupRepository.deleteById(ge.getId());
    }


    public GroupEntity findByIdOrNodFoundException(Long id) {
        return groupRepository.findById(id).orElseThrow(()
                        -> {
                    log.warn("Group id {} not found", id);
                    throw new ObjectNotFoundException("Group not found");
                }
        );
    }
}
