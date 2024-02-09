package com.pismo.api.bank.operation.service;

import com.pismo.api.bank.operation.entity.Account;
import com.pismo.api.bank.operation.exception.ExistsEntityException;
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

    private final String VALID_DOCUMENT_NUMBER = "12345678900";

    @Test
    void When_CallSaveMethod_Then_Save() {
        Account accountToSave = Account.builder()
                .documentNumber(VALID_DOCUMENT_NUMBER).build();

        Mockito.when(this.accountRepository.save(Mockito.any(Account.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        Account accountResponse = this.accountService.save(accountToSave);

        Mockito.verify(this.accountRepository, Mockito.times(1)).save(Mockito.any(Account.class));
        Assertions.assertEquals(accountResponse.getDocumentNumber(), accountToSave.getDocumentNumber());
    }

    @Test
    void When_CallSaveMethod_And_ExistsByDocumentNumber_Then_ThrowExistsEntityExceptionException() {
        Account accountToSave = Account.builder()
                .documentNumber(VALID_DOCUMENT_NUMBER).build();

        Mockito.when(this.accountRepository.existsByDocumentNumber(VALID_DOCUMENT_NUMBER))
                .thenReturn(Boolean.TRUE);

        Assertions.assertThrows(ExistsEntityException.class,() -> this.accountService.save(accountToSave));

        Mockito.verify(this.accountRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(this.accountRepository, Mockito.times(1)).existsByDocumentNumber(VALID_DOCUMENT_NUMBER);

    }





}
