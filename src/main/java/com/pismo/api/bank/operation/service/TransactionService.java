package com.pismo.api.bank.operation.service;

import com.pismo.api.bank.operation.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    Transaction save(Transaction transaction);
}
