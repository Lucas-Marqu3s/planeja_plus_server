package com.appfinanceiro.service;

import com.appfinanceiro.dto.TransactionRequest;
import com.appfinanceiro.model.Transaction;
import com.appfinanceiro.model.User;
import com.appfinanceiro.repository.TransactionRepository;
import com.appfinanceiro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public List<Transaction> getAllTransactionsByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        return transactionRepository.findByUser(user);
    }

    public List<Transaction> getTransactionsByUserAndDateRange(String email, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        return transactionRepository.findByUserAndDateBetween(user, startDate, endDate);
    }

    public List<Transaction> getTransactionsByUserAndCategory(String email, String category) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        return transactionRepository.findByUserAndCategory(user, category);
    }

    public List<Transaction> getTransactionsByUserAndType(String email, Transaction.TransactionType type) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        return transactionRepository.findByUserAndType(user, type);
    }

    public Transaction getTransactionById(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada com o id: " + id));
        
        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Transação não pertence ao usuário autenticado");
        }
        
        return transaction;
    }

    public Transaction createTransaction(TransactionRequest transactionRequest, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setType(transactionRequest.getType());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDate(transactionRequest.getDate());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setCategory(transactionRequest.getCategory());
        
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, TransactionRequest transactionRequest, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada com o id: " + id));
        
        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Transação não pertence ao usuário autenticado");
        }
        
        transaction.setType(transactionRequest.getType());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDate(transactionRequest.getDate());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setCategory(transactionRequest.getCategory());
        
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
        
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada com o id: " + id));
        
        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Transação não pertence ao usuário autenticado");
        }
        
        transactionRepository.delete(transaction);
    }
}
