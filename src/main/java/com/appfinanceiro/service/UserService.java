package com.appfinanceiro.service;

import com.appfinanceiro.model.User;
import com.appfinanceiro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o id: " + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
    }

    public User updateUser(Long id, User userDetails, String authenticatedEmail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o id: " + id));
        
        // Verificar se o usuário autenticado está tentando atualizar seu próprio perfil
        User authenticatedUser = userRepository.findByEmail(authenticatedEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + authenticatedEmail));
        
        if (!user.getId().equals(authenticatedUser.getId())) {
            throw new IllegalArgumentException("Não é permitido atualizar dados de outro usuário");
        }
        
        user.setName(userDetails.getName());
        
        // Se o email for alterado, verificar se já existe
        if (!user.getEmail().equals(userDetails.getEmail())) {
            if (userRepository.existsByEmail(userDetails.getEmail())) {
                throw new IllegalArgumentException("Email já está em uso");
            }
            user.setEmail(userDetails.getEmail());
        }
        
        return userRepository.save(user);
    }

    public User updatePassword(Long id, String currentPassword, String newPassword, String authenticatedEmail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o id: " + id));
        
        // Verificar se o usuário autenticado está tentando atualizar sua própria senha
        User authenticatedUser = userRepository.findByEmail(authenticatedEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + authenticatedEmail));
        
        if (!user.getId().equals(authenticatedUser.getId())) {
            throw new IllegalArgumentException("Não é permitido atualizar senha de outro usuário");
        }
        
        // Verificar se a senha atual está correta
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    public void deleteUser(Long id, String authenticatedEmail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o id: " + id));
        
        // Verificar se o usuário autenticado está tentando excluir seu próprio perfil
        User authenticatedUser = userRepository.findByEmail(authenticatedEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + authenticatedEmail));
        
        if (!user.getId().equals(authenticatedUser.getId())) {
            throw new IllegalArgumentException("Não é permitido excluir outro usuário");
        }
        
        userRepository.delete(user);
    }
}
