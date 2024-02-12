package com.pismo.api.bank.operation.entity;

import com.pismo.api.bank.operation.enumeration.OperationTypeEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity(name = "`transaction`")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false)
    private OperationTypeEnum operationType;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
}
