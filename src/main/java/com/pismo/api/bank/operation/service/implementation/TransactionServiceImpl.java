package com.pismo.api.bank.operation.service.implementation;

import com.pismo.api.bank.operation.entity.Account;
import com.pismo.api.bank.operation.entity.Transaction;
import com.pismo.api.bank.operation.exception.EntityNotFoundException;
import com.pismo.api.bank.operation.repository.TransactionRepository;
import com.pismo.api.bank.operation.service.AccountService;
import com.pismo.api.bank.operation.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final AccountService accountService;

    public TransactionServiceImpl(TransactionRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    @Override
    public Transaction save(Transaction transaction) {
        Account account = this.accountService.find(transaction.getAccount().getId())
                .orElseThrow(() -> new EntityNotFoundException("Account transaction not found by id: " + transaction.getAccount().getId()));
        transaction.setAccount(account);

        if (transaction.getOperationType().isDebit()) {
            transaction.setAmount(transaction.getAmount().negate());
        }

        transaction.setEventDate(LocalDateTime.now());
        return this.repository.save(transaction);
    }
}
