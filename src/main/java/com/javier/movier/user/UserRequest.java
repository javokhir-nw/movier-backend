package com.javier.movier.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequest {

    @NotBlank(message = "Username bo'sh bo'lmasligi kerak")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(min = 3, message = "Parol kamida 3 belgi bo'lishi kerak")
    private String password;
}
