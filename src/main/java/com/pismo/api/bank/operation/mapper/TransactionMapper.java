package com.pismo.api.bank.operation.mapper;

import com.pismo.api.bank.operation.dto.TransactionRequestDTO;
import com.pismo.api.bank.operation.dto.TransactionResponseDTO;
import com.pismo.api.bank.operation.entity.Account;
import com.pismo.api.bank.operation.entity.Transaction;

public class TransactionMapper {

    private TransactionMapper() {}
    public static Transaction toEntity(TransactionRequestDTO requestDTO) {
        return Transaction.builder()
                .account(Account.builder().id(requestDTO.getAccountId()).build())
                .operationType(requestDTO.getOperationType())
                .amount(requestDTO.getAmount())
                .build();
    }

    public static TransactionResponseDTO toDto(Transaction transaction) {
        return TransactionResponseDTO.builder()
                .id(transaction.getId())
                .operationType(transaction.getOperationType())
                .accountId(transaction.getAccount().getId())
                .amount(transaction.getAmount())
                .eventDate(transaction.getEventDate())
                .build();
    }
}
