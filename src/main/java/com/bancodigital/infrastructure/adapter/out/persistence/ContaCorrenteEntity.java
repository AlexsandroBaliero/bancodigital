package com.bancodigital.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CORRENTE")
public class ContaCorrenteEntity extends ContaEntity {

    @Column(precision = 19, scale = 2)
    private BigDecimal limite;

    protected ContaCorrenteEntity() {}

    public ContaCorrenteEntity(String numero, BigDecimal saldo, ClienteEntity titular, BigDecimal limite) {
        super(numero, saldo, titular);
        this.limite = limite;
    }
    public ContaCorrenteEntity(Long id, String numero, BigDecimal saldo,
                               ClienteEntity titular, BigDecimal limite) {
        super(id, numero, saldo, titular);
        this.limite = limite;
    }
    public BigDecimal getLimite() { return limite; }
}
