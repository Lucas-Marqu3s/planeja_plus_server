package com.appfinanceiro.dto;

import com.appfinanceiro.model.Transaction;
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
public class TransactionRequest {
    
    @NotNull(message = "Tipo de transação é obrigatório")
    private Transaction.TransactionType type;
    
    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal amount;
    
    @NotNull(message = "Data é obrigatória")
    private LocalDate date;
    
    @NotBlank(message = "Descrição é obrigatória")
    private String description;
    
    @NotBlank(message = "Categoria é obrigatória")
    private String category;
}
