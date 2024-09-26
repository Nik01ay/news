package example.news.service;

import example.news.entity.Role;
import example.news.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class RoleService {
    private final RoleRepository roleRepository;

    public Set<Role> findByNameSet(Set<String> roles) {

    return roles.stream()
            .map(roleRepository::findByName)
            .collect(Collectors.toSet());
    }
}
