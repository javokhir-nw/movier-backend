package com.javier.movier.auth;

import com.javier.movier.role.Permission;
import com.javier.movier.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class AuthResponseDto {
    private UUID id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
    private Set<String> permissions;
    private String token;

    public AuthResponseDto(User user, String token) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.role = user.getRole().getCode();
        this.permissions = user.getRole().getPermissions()
                .stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());
        this.token = token;
    }
}
