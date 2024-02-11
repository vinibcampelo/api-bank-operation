package com.pismo.api.bank.operation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponseDTO {
    @Schema(type = "Long", example = "1")
    @JsonProperty("account_id")
    private Long id;

    @Schema( type = "string", example = "10245678910")
    @JsonProperty("document_number")
    private String documentNumber;
}
