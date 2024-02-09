package com.pismo.api.bank.operation.service;

import com.pismo.api.bank.operation.entity.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    Account save(Account accountToSave);
}
