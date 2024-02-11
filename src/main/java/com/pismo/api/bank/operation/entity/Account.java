package com.pismo.api.bank.operation.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Digits(integer = Integer.MAX_VALUE, fraction = 0)
    @Column(name = "document_number", length = 11, nullable = false, unique = true)
    private String documentNumber;
}
