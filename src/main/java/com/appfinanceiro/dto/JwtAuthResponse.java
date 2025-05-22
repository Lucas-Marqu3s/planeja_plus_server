package com.appfinanceiro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long userId;
    private String email;
    private String name;
    
    public JwtAuthResponse(String accessToken, Long userId, String email, String name) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.email = email;
        this.name = name;
    }
}
