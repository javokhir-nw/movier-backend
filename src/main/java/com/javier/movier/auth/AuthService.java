package com.javier.movier.auth;

import com.javier.movier.auth.jwt.JwtService;
import com.javier.movier.exception.UsernameAlreadyTakenException;
import com.javier.movier.role.Role;
import com.javier.movier.role.RoleService;
import com.javier.movier.user.User;
import com.javier.movier.user.UserRequest;
import com.javier.movier.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleService roleService;

    public AuthResponseDto login(UserRequest request) {
        String username = request.getUsername();
        User user = userService.findByUsername(username);
        if (!passwordEncoder.matches(request.getPassword(),user.getPassword())) {
            log.info("Parol xato kiritildi. USERNAME: {}", username);
            throw new BadCredentialsException("Username yoki parol xato");
        }
        String token = jwtService.generateToken(user);
        return new AuthResponseDto(user,token);
    }

    public AuthResponseDto register(UserRequest request) {
        String username = request.getUsername();
        if (userService.existByUsername(username)) {
            throw new UsernameAlreadyTakenException("Bu username olingan!");
        }

        User user = new User();
        user.setUsername(request.getUsername());

        Role defaultRole = roleService.findByCode("USER");
        user.setRole(defaultRole);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userService.save(user);
        log.info("User muvaffaqiyatli ro'yhatdan o'tdi! USERNAME: {}",username);
        String token = jwtService.generateToken(user);

        return new AuthResponseDto(user,token);
    }
}
