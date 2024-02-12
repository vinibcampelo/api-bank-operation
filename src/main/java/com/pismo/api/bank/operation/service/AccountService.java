package com.pismo.api.bank.operation.service;

import com.pismo.api.bank.operation.entity.Account;

import java.util.Optional;

public interface AccountService {
    Account save(Account accountToSave);
    Optional<Account> find(Long id);
}
