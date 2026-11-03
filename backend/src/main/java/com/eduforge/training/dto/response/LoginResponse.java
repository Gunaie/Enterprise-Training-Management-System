package com.eduforge.training.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Integer id;
    private String username;
    private String name;
    private String token;
    private String role;
}