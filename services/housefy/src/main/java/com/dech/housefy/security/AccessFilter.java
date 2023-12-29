package com.dech.housefy.security;

import com.dech.housefy.dto.RoleDTO;
import com.dech.housefy.error.UnauthorizedException;
import com.dech.housefy.service.IJwtService;
import com.dech.housefy.service.IRoleService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;


@Component
@RequiredArgsConstructor
public class AccessFilter extends OncePerRequestFilter {
    private final IJwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final IRoleService roleService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String methodRequest = request.getMethod();
        final String pageRequest = request.getRequestURI();
        final String authorizationHeader = request.getHeader("Authorization");
    
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authorizationHeader.replace("Bearer ", "");
        final String email = jwtService.getEmailFromToken(token);
        UserDetails user = userDetailsService.loadUserByUsername(email);

        Collection<? extends GrantedAuthority> roles = user.getAuthorities();
        //TODO: if is admin should check their access?
        boolean hasAccess = checkAccess(roles, pageRequest, methodRequest);
        if(!hasAccess){
            throw new UnauthorizedException("User does not have access.");
        }

        filterChain.doFilter(request, response);
    }

    private boolean checkAccess(Collection<? extends GrantedAuthority> roles, String pageRequest, String methodRequest) {
        return roles.stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(role -> roleHasAccess(role, pageRequest, methodRequest));
    }

    private boolean roleHasAccess(String role, String pageRequest, String methodRequest) {
        RoleDTO roleDto = roleService.getRole(role);
        if (roleDto != null) {
            return roleDto.getPermissions().stream()
                .anyMatch(permission -> {
                    String page = permission.getPage();
                    return pageRequest.startsWith(page) && permission.getMethods().contains(methodRequest);}
                );
        }
        return false;
    }
}
