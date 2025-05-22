package com.appfinanceiro.controller;

import com.appfinanceiro.dto.GoalRequest;
import com.appfinanceiro.model.Goal;
import com.appfinanceiro.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @GetMapping
    public ResponseEntity<List<Goal>> getAllGoals(Authentication authentication) {
        return ResponseEntity.ok(goalService.getAllGoalsByUser(authentication.getName()));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Goal>> getGoalsByStatus(
            Authentication authentication,
            @PathVariable Goal.GoalStatus status) {
        return ResponseEntity.ok(goalService.getGoalsByUserAndStatus(authentication.getName(), status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoalById(
            Authentication authentication,
            @PathVariable Long id) {
        return ResponseEntity.ok(goalService.getGoalById(id, authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<Goal> createGoal(
            Authentication authentication,
            @Valid @RequestBody GoalRequest goalRequest) {
        return new ResponseEntity<>(
                goalService.createGoal(goalRequest, authentication.getName()),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody GoalRequest goalRequest) {
        return ResponseEntity.ok(
                goalService.updateGoal(id, goalRequest, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(
            Authentication authentication,
            @PathVariable Long id) {
        goalService.deleteGoal(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
