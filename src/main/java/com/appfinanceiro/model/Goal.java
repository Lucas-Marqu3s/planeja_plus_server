package com.appfinanceiro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "goals")
public class Goal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotBlank
    @Column(nullable = false)
    private String name;
    
    @NotNull
    @Positive
    @Column(name = "target_amount", nullable = false)
    private BigDecimal targetAmount;
    
    @NotNull
    @Column(name = "current_amount", nullable = false)
    private BigDecimal currentAmount;
    
    @Column(nullable = true)
    private LocalDate deadline;
    
    @Column(nullable = true)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoalStatus status;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum GoalStatus {
        IN_PROGRESS, COMPLETED, CANCELED
    }
}
