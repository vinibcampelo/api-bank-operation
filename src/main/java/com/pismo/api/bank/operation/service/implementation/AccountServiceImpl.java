package com.pismo.api.bank.operation.service.implementation;

import com.pismo.api.bank.operation.entity.Account;
import com.pismo.api.bank.operation.exception.ExistsEntityException;
import com.pismo.api.bank.operation.repository.AccountRepository;
import com.pismo.api.bank.operation.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account save(Account accountToSave) {
        if (this.repository.existsByDocumentNumber(accountToSave.getDocumentNumber())) {
            throw new ExistsEntityException("An account with this document number already exists.");
        }
        return this.repository.save(accountToSave);
    }

}
