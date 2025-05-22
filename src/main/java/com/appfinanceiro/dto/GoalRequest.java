package com.appfinanceiro.dto;

import com.appfinanceiro.model.Goal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    
    @NotNull(message = "Valor alvo é obrigatório")
    @Positive(message = "Valor alvo deve ser positivo")
    private BigDecimal targetAmount;
    
    @NotNull(message = "Valor atual é obrigatório")
    @Positive(message = "Valor atual deve ser positivo")
    private BigDecimal currentAmount;
    
    private LocalDate deadline;
    
    private String description;
    
    @NotNull(message = "Status é obrigatório")
    private Goal.GoalStatus status;
}
