package com.pismo.api.bank.operation.service;

import com.pismo.api.bank.operation.entity.Account;
import com.pismo.api.bank.operation.entity.Transaction;
import com.pismo.api.bank.operation.enumeration.OperationTypeEnum;
import com.pismo.api.bank.operation.exception.EntityNotFoundException;
import com.pismo.api.bank.operation.repository.TransactionRepository;
import com.pismo.api.bank.operation.service.implementation.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private TransactionServiceImpl transactionService;
    private Account account;
    private Transaction transaction;
    @BeforeEach
    public void setup() {
        this.account = Account.builder()
                .id(1L)
                .build();

        this.transaction = Transaction.builder()
                .account(account)
                .operationType(OperationTypeEnum.PAGAMENTO)
                .amount(BigDecimal.valueOf(100))
                .build();
    }

    @Test
    void When_CallSaveMethod_Then_Save() {
        Mockito.when(this.accountService.find(account.getId())).thenReturn(Optional.of(account));
        Mockito.when(this.transactionRepository.save(Mockito.any(Transaction.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        Transaction transactionSaved = this.transactionService.save(transaction);

        Assertions.assertNotNull(transactionSaved);
        Assertions.assertEquals(transaction.getAmount(),transactionSaved.getAmount());
        Assertions.assertTrue(LocalDateTime.now().isAfter(transactionSaved.getEventDate()));
        Assertions.assertEquals(transaction.getOperationType(), transactionSaved.getOperationType());

        Mockito.verify(accountService, Mockito.times(1)).find(account.getId());
        Mockito.verify(transactionRepository, Mockito.times(1))
                .save(Mockito.any(Transaction.class));

    }    @Test
    void When_CallSaveMethod_And_AccountNotExists_Then_ThrowEntityNotFoundException() {
        Mockito.when(this.accountService.find(account.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> this.transactionService.save(transaction));

        Mockito.verify(accountService, Mockito.times(1)).find(account.getId());
        Mockito.verify(transactionRepository, Mockito.times(0))
                .save(Mockito.any(Transaction.class));

    }
    @Test
    void When_CallSaveMethod_And_OperationTypeIsDebit_Then_ExpectSaveAmountNegative() {
        List<OperationTypeEnum> operationTypeEnumList = Arrays.asList(
                OperationTypeEnum.COMPRA_A_VISTA,
                OperationTypeEnum.SAQUE,
                OperationTypeEnum.COMPRA_PARCELADA
        );

        for (OperationTypeEnum operationType : operationTypeEnumList) {
            Transaction transaction = Transaction.builder()
                    .account(account)
                    .operationType(operationType)
                    .amount(BigDecimal.valueOf(100))
                    .build();

            Mockito.when(this.accountService.find(account.getId())).thenReturn(Optional.of(account));
            Mockito.when(this.transactionRepository.save(Mockito.any(Transaction.class)))
                    .thenAnswer(AdditionalAnswers.returnsFirstArg());

            Transaction transactionSaved = this.transactionService.save(transaction);

            Assertions.assertTrue(transactionSaved.getAmount().compareTo(BigDecimal.ZERO) <= 0);
        }

        Mockito.verify(accountService, Mockito.times(3)).find(account.getId());
        Mockito.verify(transactionRepository, Mockito.times(3))
                .save(Mockito.any(Transaction.class));

    }
}
