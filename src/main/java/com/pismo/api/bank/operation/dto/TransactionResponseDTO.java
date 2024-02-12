package com.pismo.api.bank.operation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.api.bank.operation.enumeration.OperationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@Getter
public class TransactionResponseDTO {

    private Long id;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("operation_type")
    private OperationTypeEnum operationType;

    private BigDecimal amount;

    @JsonProperty("event_date")
    private LocalDateTime eventDate;
}

