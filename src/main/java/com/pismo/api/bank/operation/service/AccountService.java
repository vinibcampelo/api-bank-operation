package com.pismo.api.bank.operation.service;

import com.pismo.api.bank.operation.dto.AccountDTO;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

    AccountDTO save(AccountDTO accountRequest);
}
