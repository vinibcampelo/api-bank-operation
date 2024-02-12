package com.pismo.api.bank.operation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.api.bank.operation.dto.TransactionRequestDTO;
import com.pismo.api.bank.operation.entity.Transaction;
import com.pismo.api.bank.operation.enumeration.OperationTypeEnum;
import com.pismo.api.bank.operation.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    TransactionService transactionService;

    private final String TRANSACTION_URL = "/transactions";

    @Test
    void When_CallPostCreateMethod_Then_ExpectResponseStatusCreated() throws Exception {
        TransactionRequestDTO requestBody = TransactionRequestDTO.builder()
                .accountId(1L)
                .amount(BigDecimal.valueOf(123.45))
                .operationType(OperationTypeEnum.PAGAMENTO)
                .build();
        Mockito.when(this.transactionService.save(Mockito.any(Transaction.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        mockMvc.perform(MockMvcRequestBuilders.post(TRANSACTION_URL)
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(transactionService, Mockito.times(1)).save(Mockito.any(Transaction.class));
    }

    @Test
    void When_CallPostCreateMethod_And_RequestValuesIsNull_Then_ExpectBadRequest() throws Exception {
        TransactionRequestDTO requestBody = new TransactionRequestDTO();

        mockMvc.perform(MockMvcRequestBuilders.post(TRANSACTION_URL)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().string(containsString("amount: must not be null")))
                .andExpect(content().string(containsString("operationType: must not be null")))
                .andExpect(content().string(containsString("accountId: must not be null")));

        Mockito.verify(transactionService, Mockito.times(0)).save(Mockito.any());
    }
    @Test
    void When_CallPostCreateMethod_And_RequestValuesIsNegative_Then_ExpectBadRequest() throws Exception {
        TransactionRequestDTO requestBody = TransactionRequestDTO.builder()
                .amount(BigDecimal.valueOf(-1))
                .accountId(-1L)
                .operationType(OperationTypeEnum.PAGAMENTO)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(TRANSACTION_URL)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().string(containsString("accountId: must be greater than 0")))
                .andExpect(content().string(containsString("amount: must be greater than 0")));

        Mockito.verify(transactionService, Mockito.times(0)).save(Mockito.any());
    }
    @Test
    void When_CallPostCreateMethod_And_AmountHaveMoreThenTwoFractions_Then_ExpectBadRequest() throws Exception {
        TransactionRequestDTO requestBody = TransactionRequestDTO.builder()
                .amount(BigDecimal.valueOf(128.689))
                .accountId(1L)
                .operationType(OperationTypeEnum.PAGAMENTO)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(TRANSACTION_URL)
                        .content(objectMapper.writeValueAsString(requestBody))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().string(containsString("amount: numeric value out of bounds (<16 digits>.<2 digits> expected)")));

        Mockito.verify(transactionService, Mockito.times(0)).save(Mockito.any());
    }

}
