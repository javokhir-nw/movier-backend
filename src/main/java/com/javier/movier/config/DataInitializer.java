package com.javier.movier.config;

import com.javier.movier.role.Permission;
import com.javier.movier.role.PermissionRepository;
import com.javier.movier.role.Role;
import com.javier.movier.role.RoleRepository;
import com.javier.movier.user.User;
import com.javier.movier.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    @Value("${init.username}")
    private String initUsername;

    @Value("${init.password}")
    private String initPassword;

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final ApplicationContext context;

    private static final Pattern PATTERN = Pattern.compile("hasAuthority\\('([^']+)'\\)");
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String @NonNull ... args) {
        Set<Permission> list = scanAndSavePermissions();
        Role adminRole = initRole("admin", "ADMIN", list);
        initUser(initUsername, adminRole);
    }

    private void initUser(String username, Role role) {
        if (!userRepository.existsByUsername(username)) {
            User u = new User();
            u.setUsername(username);
            u.setPassword(passwordEncoder.encode(initPassword));
            u.setRole(role);
            userRepository.save(u);
        }
    }

    private Role initRole(String name, String code, Set<Permission> permissions) {
        Optional<Role> byCode = roleRepository.findByCode(code);
        Role role;
        if (byCode.isEmpty()){
            role = new Role(null, name, code, permissions);
        } else {
            role = byCode.get();
            role.setPermissions(permissions);
        }
        role = roleRepository.save(role);
        return role;
    }

    public Set<Permission> scanAndSavePermissions() {
        log.info("Permissionlarni saqlash boshlandi. TIME: {}",new Date());

        Set<Permission> permissions = new HashSet<>();

        Map<String, Permission> existPermissions = permissionRepository.findAll().stream()
                .collect(Collectors.toMap(Permission::getName, Function.identity()));

        context.getBeansWithAnnotation(RestController.class).values().forEach(bean -> {
            for (Method method : bean.getClass().getMethods()) {
                PreAuthorize annotation = method.getAnnotation(PreAuthorize.class);
                if (annotation != null) {
                    Matcher matcher = PATTERN.matcher(annotation.value());
                    while (matcher.find()) {
                        String name = matcher.group(1);
                        if (!existPermissions.containsKey(name)){
                            Permission p = new Permission();
                            p.setName(name);
                            permissions.add(p);
                        }
                    }
                }
            }
        });

        permissionRepository.saveAll(permissions);

        log.info("Permissionlarni saqlash tugadi. TIME: {}",new Date());


        permissions.addAll(existPermissions.values());

        return permissions;
    }
}
