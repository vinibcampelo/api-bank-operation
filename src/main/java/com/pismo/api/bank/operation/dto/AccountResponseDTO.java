package com.pismo.api.bank.operation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponseDTO {
    @Schema(type = "Long", example = "1")
    private Long id;
    @Schema( type = "string", example = "10245678910")
    private String documentNumber;
}
