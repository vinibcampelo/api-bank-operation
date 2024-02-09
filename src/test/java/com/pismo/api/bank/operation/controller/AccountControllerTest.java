package com.pismo.api.bank.operation.controller;

import com.pismo.api.bank.operation.dto.AccountDTO;
import com.pismo.api.bank.operation.entity.Account;
import com.pismo.api.bank.operation.mapper.AccountMapper;
import com.pismo.api.bank.operation.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    void When_CallCreateMethod_And_AccountIsValid_Then_ReturnCreatedResponse() {
        AccountDTO accountDTO = new AccountDTO("12345678910");

        Mockito.when(accountService.save(Mockito.any(Account.class)))
                .thenReturn(AccountMapper.toEntity(accountDTO));

        ResponseEntity<AccountDTO> response = this.accountController.create(accountDTO);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(accountDTO.getDocumentNumber(), response.getBody().getDocumentNumber());

        Mockito.verify(this.accountService, Mockito.times(1))
                .save(Mockito.any(Account.class));
    }
}
