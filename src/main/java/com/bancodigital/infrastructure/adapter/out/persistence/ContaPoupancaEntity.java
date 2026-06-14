package com.bancodigital.infrastructure.adapter.out.persistence;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("POUPANCA")
public class ContaPoupancaEntity extends ContaEntity {

    protected ContaPoupancaEntity() {}

    public ContaPoupancaEntity(String numero, BigDecimal saldo, ClienteEntity titular) {
        super(numero, saldo, titular);
    }
    public ContaPoupancaEntity(Long id, String numero, BigDecimal saldo, ClienteEntity titular) {
        super(id, numero, saldo, titular);
    }
}