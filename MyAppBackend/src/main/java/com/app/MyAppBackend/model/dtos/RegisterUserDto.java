package com.app.MyAppBackend.model.dtos;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String email;
    private String username;
    private String password;
}
