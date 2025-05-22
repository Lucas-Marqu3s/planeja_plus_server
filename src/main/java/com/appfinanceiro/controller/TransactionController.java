package com.appfinanceiro.controller;

import com.appfinanceiro.dto.TransactionRequest;
import com.appfinanceiro.model.Transaction;
import com.appfinanceiro.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(Authentication authentication) {
        return ResponseEntity.ok(transactionService.getAllTransactionsByUser(authentication.getName()));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
            Authentication authentication,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(transactionService.getTransactionsByUserAndDateRange(
                authentication.getName(), startDate, endDate));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Transaction>> getTransactionsByCategory(
            Authentication authentication,
            @PathVariable String category) {
        return ResponseEntity.ok(transactionService.getTransactionsByUserAndCategory(
                authentication.getName(), category));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Transaction>> getTransactionsByType(
            Authentication authentication,
            @PathVariable Transaction.TransactionType type) {
        return ResponseEntity.ok(transactionService.getTransactionsByUserAndType(
                authentication.getName(), type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(
            Authentication authentication,
            @PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id, authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(
            Authentication authentication,
            @Valid @RequestBody TransactionRequest transactionRequest) {
        return new ResponseEntity<>(
                transactionService.createTransaction(transactionRequest, authentication.getName()),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(
                transactionService.updateTransaction(id, transactionRequest, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            Authentication authentication,
            @PathVariable Long id) {
        transactionService.deleteTransaction(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
