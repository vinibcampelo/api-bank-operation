package com.pismo.api.bank.operation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDTO {

    @NotBlank
    @Size(min = 11, max = 11)
    @Digits(integer = Integer.MAX_VALUE, fraction = 0)
    @Schema( type = "string", example = "10245678910")
    @JsonProperty("document_number")
    private String documentNumber;
}
