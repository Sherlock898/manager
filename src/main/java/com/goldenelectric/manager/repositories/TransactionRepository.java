package com.goldenelectric.manager.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.goldenelectric.manager.models.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long>{
    List<Transaction> findByUserId(Long userId);
}
