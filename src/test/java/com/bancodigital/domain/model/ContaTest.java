package com.bancodigital.domain.model;

import com.bancodigital.domain.exception.SaldoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Testes da classe Conta")
class ContaTest {

    private Cliente cliente;
    private ContaCorrente contaCorrente;
    private ContaPoupanca contaPoupanca;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Alex", "12345678901");
        contaCorrente = new ContaCorrente("0001", cliente, new BigDecimal("500.00"));
        contaPoupanca = new ContaPoupanca("0002", cliente);
    }

    @Test
    @DisplayName("Deve depositar valor válido")
    void deveDepositarValorValido() {
        contaPoupanca.depositar(new BigDecimal("100.00"));
        assertThat(contaPoupanca.getSaldo()).isEqualByComparingTo("100.00");
    }

    @Test
    @DisplayName("Deve lançar exceção ao sacar com saldo insuficiente")
    void deveLancarExcecaoSaldoInsuficiente() {
        assertThatThrownBy(() -> contaPoupanca.sacar(new BigDecimal("100.00")))
                .isInstanceOf(SaldoInsuficienteException.class);
    }

    @Test
    @DisplayName("Conta corrente deve usar limite no saque")
    void deveUsarLimiteNoSaque() {
        contaCorrente.depositar(new BigDecimal("100.00"));
        contaCorrente.sacar(new BigDecimal("550.00"));
        assertThat(contaCorrente.getSaldo()).isEqualByComparingTo("-450.00");
    }
}