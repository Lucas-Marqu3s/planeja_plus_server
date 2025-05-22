package com.appfinanceiro.service;

import com.appfinanceiro.dto.GoalRequest;
import com.appfinanceiro.model.Goal;
import com.appfinanceiro.model.User;
import com.appfinanceiro.repository.GoalRepository;
import com.appfinanceiro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public List<Goal> getAllGoalsByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        return goalRepository.findByUser(user);
    }

    public List<Goal> getGoalsByUserAndStatus(String email, Goal.GoalStatus status) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        return goalRepository.findByUserAndStatus(user, status);
    }

    public Goal getGoalById(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meta não encontrada com o id: " + id));
        
        if (!goal.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Meta não pertence ao usuário autenticado");
        }
        
        return goal;
    }

    public Goal createGoal(GoalRequest goalRequest, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        
        Goal goal = new Goal();
        goal.setUser(user);
        goal.setName(goalRequest.getName());
        goal.setTargetAmount(goalRequest.getTargetAmount());
        goal.setCurrentAmount(goalRequest.getCurrentAmount());
        goal.setDeadline(goalRequest.getDeadline());
        goal.setDescription(goalRequest.getDescription());
        goal.setStatus(goalRequest.getStatus());
        
        return goalRepository.save(goal);
    }

    public Goal updateGoal(Long id, GoalRequest goalRequest, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meta não encontrada com o id: " + id));
        
        if (!goal.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Meta não pertence ao usuário autenticado");
        }
        
        goal.setName(goalRequest.getName());
        goal.setTargetAmount(goalRequest.getTargetAmount());
        goal.setCurrentAmount(goalRequest.getCurrentAmount());
        goal.setDeadline(goalRequest.getDeadline());
        goal.setDescription(goalRequest.getDescription());
        goal.setStatus(goalRequest.getStatus());
        
        return goalRepository.save(goal);
    }

    public void deleteGoal(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meta não encontrada com o id: " + id));
        
        if (!goal.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Meta não pertence ao usuário autenticado");
        }
        
        goalRepository.delete(goal);
    }
}
