package com.javier.movier.role;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findByCode(String code) {
        return roleRepository.findByCode(code)
                .orElseThrow(() -> {
                    log.info("Bunday rol mavjud emas! CODE: {}",code);
                    return new EntityNotFoundException("Bunday rol mavjud emas!");
                });
    }
}
