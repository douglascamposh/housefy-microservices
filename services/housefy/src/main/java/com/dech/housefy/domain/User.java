package com.dech.housefy.domain;

import com.dech.housefy.enums.PermissionEnums;
import com.dech.housefy.enums.RoleEnums;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class User extends BaseUser implements UserDetails {

    @NotBlank
    private String email;
    private LocalDate birthDate;
    private String password;

    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String roleName: roles) {
            authorities.add(new SimpleGrantedAuthority(RoleEnums.valueOf(roleName).name()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
