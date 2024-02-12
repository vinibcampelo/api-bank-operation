package com.pismo.api.bank.operation.enumeration;

public enum OperationTypeEnum {
    COMPRA_A_VISTA(true),
    COMPRA_PARCELADA(true),
    SAQUE(true),
    PAGAMENTO(false);

    private final boolean isDebit;

    OperationTypeEnum(boolean isDebit) {
        this.isDebit = isDebit;
    }

    public boolean isDebit() {
        return isDebit;
    }

}
