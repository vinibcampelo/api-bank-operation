package com.pismo.api.bank.operation.controller;

import com.pismo.api.bank.operation.dto.TransactionRequestDTO;
import com.pismo.api.bank.operation.dto.TransactionResponseDTO;
import com.pismo.api.bank.operation.entity.Transaction;
import com.pismo.api.bank.operation.mapper.TransactionMapper;
import com.pismo.api.bank.operation.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@Valid @RequestBody TransactionRequestDTO requestDTO) {
        Transaction transaction = this.transactionService.save(TransactionMapper.toEntity(requestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionMapper.toDto(transaction));
    }
}
