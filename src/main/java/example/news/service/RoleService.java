package example.news.service;

import example.news.entity.Role;
import example.news.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class RoleService {
    @Autowired
    private final RoleRepository roleRepository;

    public Optional<Set<Role>> findByNameSet(Set<String> roles) {

        return Optional.ofNullable(roles.stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toSet()));
    }

    public Set<Role> addRoleSet(Set<String> roleNameSet) {

        roleNameSet.

                forEach(roleName -> {

                    if (findByName(roleName).isEmpty()) {
                        var role = new Role();
                        role.setName(roleName);
                        roleRepository.save(
                                role);

                    }
                });
        return  findByNameSet(roleNameSet).orElseThrow();
    }

    public Optional<Role> findByName(String roleName) {
        return Optional.ofNullable(roleRepository.findByName(roleName));
    }
}
