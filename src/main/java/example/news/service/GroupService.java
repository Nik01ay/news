package example.news.service;

import example.news.entity.GroupEntity;
import example.news.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class GroupService {
    @Autowired
    GroupRepository groupRepository;
    public GroupEntity getByNewsId(Long id) {
        return groupRepository.getReferenceById(id);
    }

}
