package example.news.security;


import example.news.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AppUserPrincipal implements UserDetails {

    private final UserDto user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getPermission().stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        /*return user.getRoles().stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

         */
        //todo убрал роли, поменял на права т.к. это более гибко
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
