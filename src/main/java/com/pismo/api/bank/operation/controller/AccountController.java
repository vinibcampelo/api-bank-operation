package com.pismo.api.bank.operation.controller;

import com.pismo.api.bank.operation.dto.AccountRequestDTO;
import com.pismo.api.bank.operation.dto.AccountResponseDTO;
import com.pismo.api.bank.operation.entity.Account;
import com.pismo.api.bank.operation.mapper.AccountMapper;
import com.pismo.api.bank.operation.service.AccountService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Validated
@RestController
@RequestMapping("account")
public class AccountController {
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AccountRequestDTO> create(@Valid @RequestBody AccountRequestDTO request) {
        Account accountSaved = this.service.save(AccountMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountMapper.toRequestDTO(accountSaved));
    }

    @GetMapping("{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "204", content = @Content())
    })
    public ResponseEntity<AccountResponseDTO> findById(@Valid @NotNull @PathVariable Long id){
        Optional<Account> account = this.service.find(id);

        if (account.isPresent()) {
            return ResponseEntity.ok(AccountMapper.toResponseDTO(account.get()));
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
