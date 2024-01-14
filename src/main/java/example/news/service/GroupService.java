package example.news.service;

import example.news.dto.GroupDto;
import example.news.entity.GroupEntity;
import example.news.mapper.GroupMapper;
import example.news.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public GroupEntity getById(Long groupId) {
        return groupRepository.findById(groupId).orElse(new GroupEntity());
    }
}
