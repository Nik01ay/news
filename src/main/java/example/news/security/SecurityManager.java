package example.news.security;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

@Service

public class SecurityManager {

    public void checkAccess(Long ownerId, @AuthenticationPrincipal AppUserPrincipal userDetails) {
        boolean hasAccess = userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")
                        || grantedAuthority.getAuthority().equals("ROLE_MODERATOR"))
                || (ownerId.equals(userDetails.getId()));

        if (!hasAccess) {
            throw new AccessDeniedException("Access denied");
        }
    }
    public  void checkAuthor(Long ownerId, @AuthenticationPrincipal AppUserPrincipal userDetails) {
        if (!ownerId.equals(userDetails.getId())){
            throw new AccessDeniedException("Access denied");
        }
    }
}


