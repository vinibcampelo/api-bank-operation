package com.pismo.api.bank.operation.mapper;

import com.pismo.api.bank.operation.dto.AccountRequestDTO;
import com.pismo.api.bank.operation.dto.AccountResponseDTO;
import com.pismo.api.bank.operation.entity.Account;

public class AccountMapper {
    private AccountMapper() {}
    public static AccountRequestDTO toRequestDTO(Account account) {
        return AccountRequestDTO.builder()
                .documentNumber(account.getDocumentNumber())
                .build();
    }

    public static AccountResponseDTO toResponseDTO(Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId())
                .documentNumber(account.getDocumentNumber())
                .build();
    }

    public static Account toEntity(AccountRequestDTO accountRequestDTO) {
        return Account.builder()
                .documentNumber(accountRequestDTO.getDocumentNumber())
                .build();
    }
}
