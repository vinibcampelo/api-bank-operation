package com.pismo.api.bank.operation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.api.bank.operation.dto.AccountRequestDTO;
import com.pismo.api.bank.operation.entity.Account;
import com.pismo.api.bank.operation.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AccountService accountService;

    private final String ACCOUNT_URL = "/account";

    @Test
    void When_CallCreateRequest_And_AccountIsValid_Then_ReturnCreatedResponse() throws Exception {
        AccountRequestDTO request = new AccountRequestDTO("12345678910");

        Mockito.when(accountService.save(Mockito.any(Account.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        this.mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(
                        content().string(containsString(request.getDocumentNumber()))
                );

        Mockito.verify(accountService, Mockito.times(1)).save(Mockito.any(Account.class));
    }
    @Test
    void When_CallCreateRequest_And_DocumentNumberIsNull_Then_ExpectedErrorMessageResponse() throws Exception {
        AccountRequestDTO request = new AccountRequestDTO();

        this.mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(
                        content().string(containsString("Document number cold not be null or empty")
                        )
                    );

        Mockito.verify(this.accountService, Mockito.times(0)).save(Mockito.any());
    }
    @Test
    void When_CallCreateRequest_And_DocumentNumberIsEmpty_Then_ExpectedErrorMessageResponse() throws Exception {
        AccountRequestDTO request = new AccountRequestDTO("");

        mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content().string(containsString("Document number cold not be null or empty")
                        )
                );

        Mockito.verify(this.accountService, Mockito.times(0)).save(Mockito.any());
    }
    @Test
    void When_CallCreateRequest_And_DocumentNumberIsNotANumber_Then_ExpectedErrorMessageResponse() throws Exception {
        AccountRequestDTO request = new AccountRequestDTO("abc45678910");

        mockMvc.perform(MockMvcRequestBuilders.post(ACCOUNT_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content().string(containsString("Document number need be a number")
                        )
                );

        Mockito.verify(this.accountService, Mockito.times(0)).save(Mockito.any());
    }
    @Test
    void When_CallCreateRequest_And_DocumentNumberSizeIsLessThen11_Then_ExpectedErrorMessageResponse() throws Exception {
        AccountRequestDTO request = new AccountRequestDTO("1234");

        this.mockMvc.perform(MockMvcRequestBuilders.post(this.ACCOUNT_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content().string(containsString("Document number size is not valid")
                        )
                );

        Mockito.verify(this.accountService, Mockito.times(0)).save(Mockito.any());
    }
    @Test
    void When_CallCreateRequest_And_DocumentNumberSizeIsMoreThen11_Then_ExpectedErrorMessageResponse() throws Exception {
        AccountRequestDTO request = new AccountRequestDTO("123456789123");

        this.mockMvc.perform(MockMvcRequestBuilders.post(this.ACCOUNT_URL)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(
                        content().string(containsString("Document number size is not valid")
                        )
                );

        Mockito.verify(this.accountService, Mockito.times(0)).save(Mockito.any());
    }
    @Test
    void When_CallGetByIdRequest_And_ExistsById_Then_ExpectStatusOk() throws Exception {
        Long id = 1L;
        String documentNumber = "12345678910";
        Account account = Account.builder()
                .id(id)
                .documentNumber(documentNumber)
                .build();

        Mockito.when(this.accountService.find(id)).thenReturn(Optional.of(account));

        this.mockMvc.perform(MockMvcRequestBuilders.get(this.ACCOUNT_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(documentNumber)))
                .andExpect(content().string(containsString(id.toString())));

        Mockito.verify(this.accountService, Mockito.times(1)).find(id);
    }
    @Test
    void When_CallGetByIdRequest_And_NotExistsById_Then_ExpectStatusNoContent() throws Exception {
        Long id = 1L;

        Mockito.when(this.accountService.find(id)).thenReturn(Optional.empty());

        this.mockMvc.perform(MockMvcRequestBuilders.get(this.ACCOUNT_URL + "/" + id))
                .andExpect(status().isNoContent());

        Mockito.verify(this.accountService, Mockito.times(1)).find(id);
    }


}
