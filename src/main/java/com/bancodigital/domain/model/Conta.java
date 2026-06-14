package com.bancodigital.domain.model;

import com.bancodigital.domain.exception.SaldoInsuficienteException;
import com.bancodigital.domain.exception.ValorInvalidoException;

import java.math.BigDecimal;

public abstract class Conta {
    private final String numero;
    private final Cliente titular;
    private BigDecimal saldo = BigDecimal.ZERO;

    protected Conta(String numero, Cliente titular) {
        this.numero = numero;
        this.titular = titular;
    }

    public void depositar(BigDecimal valor) {
        validarValor(valor);
        saldo = saldo.add(valor);
    }

    public void sacar(BigDecimal valor) {
        validarValor(valor);
        if (saldo.compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }
        saldo = saldo.subtract(valor);
    }

    protected void validarValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("O valor deve ser maior que zero.");
        }
    }

    protected void debitar(BigDecimal valor) { // usado pelas subclasses
        saldo = saldo.subtract(valor);
    }

    public String getNumero() {
        return numero;
    }

    public Cliente getTitular() {
        return titular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}