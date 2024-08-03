package com.goldenelectric.manager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.goldenelectric.manager.repositories.TransactionRepository;

import com.goldenelectric.manager.models.Transaction;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Get all transactions
    public List<Transaction> findAll() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    // Get transaction by id
    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    // Create transaction
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Update transaction
    public Transaction update(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Delete transaction
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    // Get transactions by user id
    public List<Transaction> findByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}
