package com.pismo.api.bank.operation.mapper;

import com.pismo.api.bank.operation.dto.AccountDTO;
import com.pismo.api.bank.operation.entity.Account;

public class AccountMapper {
    private AccountMapper() {}
    public static AccountDTO toDTO(Account account) {
        return AccountDTO.builder()
                .documentNumber(account.getDocumentNumber())
                .build();
    }

    public static Account toEntity(AccountDTO accountDTO) {
        return Account.builder()
                .documentNumber(accountDTO.getDocumentNumber())
                .build();
    }
}
