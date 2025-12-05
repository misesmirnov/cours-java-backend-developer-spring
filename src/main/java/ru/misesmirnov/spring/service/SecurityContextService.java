package ru.misesmirnov.spring.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.misesmirnov.spring.entity.type.UserRoleEnum;

import java.util.Collection;

@Service
public class SecurityContextService {

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    public UserRoleEnum getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (!authorities.isEmpty()) {
                String role = authorities.iterator().next().getAuthority();
                return UserRoleEnum.valueOf(role.replace("ROLE_", ""));
            }
        }
        return null;
    }
}
