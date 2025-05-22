package com.appfinanceiro.controller;

import com.appfinanceiro.dto.PasswordUpdateRequest;
import com.appfinanceiro.dto.UserUpdateRequest;
import com.appfinanceiro.model.User;
import com.appfinanceiro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserByEmail(authentication.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            Authentication authentication,
            @PathVariable Long id) {
        // Verificação de segurança feita no serviço
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        User userDetails = new User();
        userDetails.setName(userUpdateRequest.getName());
        userDetails.setEmail(userUpdateRequest.getEmail());
        
        return ResponseEntity.ok(userService.updateUser(id, userDetails, authentication.getName()));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<User> updatePassword(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        return ResponseEntity.ok(userService.updatePassword(
                id, 
                passwordUpdateRequest.getCurrentPassword(), 
                passwordUpdateRequest.getNewPassword(), 
                authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            Authentication authentication,
            @PathVariable Long id) {
        userService.deleteUser(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
