package com.bancodigital.domain.model;

import com.bancodigital.domain.exception.SaldoInsuficienteException;

import java.math.BigDecimal;

public class ContaCorrente extends Conta {
    private final BigDecimal limite;

    public ContaCorrente(String numero, Cliente titular, BigDecimal limite) {
        super(numero, titular);
        this.limite = limite;
    }

    @Override
    public void sacar(BigDecimal valor) {
        validarValor(valor);
        BigDecimal disponivel = getSaldo().add(limite);
        if (disponivel.compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo + limite insuficientes.");
        }
        debitar(valor); // pode deixar o saldo negativo, usando o limite
    }

    public BigDecimal getLimite() {
        return limite;
    }
}