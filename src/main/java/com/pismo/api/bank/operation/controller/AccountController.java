package com.pismo.api.bank.operation.controller;

import com.pismo.api.bank.operation.dto.AccountDTO;
import com.pismo.api.bank.operation.entity.Account;
import com.pismo.api.bank.operation.mapper.AccountMapper;
import com.pismo.api.bank.operation.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("account")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO request) {
        Account accountSaved = this.service.save(AccountMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountMapper.toDTO(accountSaved));
    }
}
