package com.pismo.api.bank.operation.service;

import com.pismo.api.bank.operation.dto.AccountDTO;
import com.pismo.api.bank.operation.entity.Account;
import com.pismo.api.bank.operation.repository.AccountRepository;
import com.pismo.api.bank.operation.service.implementation.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void When_CallSaveMethod_Then_Save() {
        AccountDTO accountRequest = AccountDTO.builder()
                .documentNumber("12345678900").build();

        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        AccountDTO accountResponse = accountService.save(accountRequest);

        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any(Account.class));
        Assertions.assertEquals(accountResponse.getDocumentNumber(), accountRequest.getDocumentNumber());
    }

    //TODO: retornar um erro se já existir o account para aquele documento





}
