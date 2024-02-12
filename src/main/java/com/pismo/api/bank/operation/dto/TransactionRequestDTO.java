package com.pismo.api.bank.operation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.api.bank.operation.enumeration.OperationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDTO {

    @Positive
    @NotNull
    @JsonProperty("account_id")
    @Schema(type = "integer", example = "1")
    private Long accountId;

    @NotNull
    @JsonProperty("operation_type")
    @Enumerated(EnumType.STRING)
    @Schema( type = "string", example = "COMPRA_A_VISTA|COMPRA_PARCELADA|SAQUE|PAGAMENTO")
    private OperationTypeEnum operationType;

    @Positive
    @NotNull
    @Digits(integer = 16, fraction = 2)
    @Schema(type = "number", example = "123.45")
    private BigDecimal amount;
}
