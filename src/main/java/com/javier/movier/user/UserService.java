package com.javier.movier.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.info("Bunday user mavjud emas! USERNAME: {}", username);
                    return new EntityNotFoundException("Bunday user mavjud emas!");
                });
    }

    public Boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User save(User user) {
      return userRepository.save(user);
    }
}
