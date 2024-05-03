package com.example.demo.dtos.Login;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String jwt;
    private Integer id;
    private String role;

}
