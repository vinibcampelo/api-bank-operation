package com.pismo.api.bank.operation.service.implementation;

import com.pismo.api.bank.operation.dto.AccountDTO;
import com.pismo.api.bank.operation.entity.Account;
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
    public AccountDTO save(AccountDTO accountRequest) {
        Account account = Account.builder()
                .documentNumber(accountRequest.getDocumentNumber()).build();

        return AccountDTO.builder().documentNumber(this.repository.save(account).getDocumentNumber()).build();
    }
}
