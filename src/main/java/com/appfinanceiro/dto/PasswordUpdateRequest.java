package com.appfinanceiro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateRequest {
    
    @NotBlank(message = "Senha atual é obrigatória")
    private String currentPassword;
    
    @NotBlank(message = "Nova senha é obrigatória")
    @Size(min = 6, max = 120, message = "Nova senha deve ter entre 6 e 120 caracteres")
    private String newPassword;
}
