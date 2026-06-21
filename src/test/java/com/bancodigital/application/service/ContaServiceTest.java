package com.bancodigital.application.service;

import com.bancodigital.domain.exception.RecursoNaoEncontradoException;
import com.bancodigital.domain.exception.SaldoInsuficienteException;
import com.bancodigital.domain.model.Cliente;
import com.bancodigital.domain.model.Conta;
import com.bancodigital.domain.model.ContaCorrente;
import com.bancodigital.domain.model.ContaPoupanca;
import com.bancodigital.domain.port.out.ClienteRepositoryPort;
import com.bancodigital.domain.port.out.ContaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ContaService")
class ContaServiceTest {

    @Mock
    private ContaRepositoryPort contaRepository;

    @Mock
    private ClienteRepositoryPort clienteRepository;

    @InjectMocks
    private ContaService contaService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Alex", "12345678901");
    }

    @Test
    @DisplayName("Deve abrir conta corrente com sucesso")
    void deveAbrirContaCorrenteComSucesso() {
        when(clienteRepository.buscarPorCpf("12345678901")).thenReturn(Optional.of(cliente));
        when(contaRepository.salvar(any(Conta.class))).thenAnswer(invocacao -> invocacao.getArgument(0));

        Conta conta = contaService.abrir("12345678901", "0001", "CORRENTE", "500.00");

        assertThat(conta).isInstanceOf(ContaCorrente.class);
        assertThat(conta.getNumero()).isEqualTo("0001");
        verify(contaRepository).salvar(any(Conta.class));
    }

    @Test
    @DisplayName("Deve abrir conta poupança com sucesso")
    void deveAbrirContaPoupancaComSucesso() {
        when(clienteRepository.buscarPorCpf("12345678901")).thenReturn(Optional.of(cliente));
        when(contaRepository.salvar(any(Conta.class))).thenAnswer(invocacao -> invocacao.getArgument(0));

        Conta conta = contaService.abrir("12345678901", "0002", "POUPANCA", null);

        assertThat(conta).isInstanceOf(ContaPoupanca.class);
    }

    @Test
    @DisplayName("Deve lançar exceção ao abrir conta para cliente inexistente")
    void deveLancarExcecaoParaClienteInexistente() {
        when(clienteRepository.buscarPorCpf("99999999999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contaService.abrir("99999999999", "0003", "CORRENTE", "0"))
                .isInstanceOf(RecursoNaoEncontradoException.class);

        verify(contaRepository, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve depositar com sucesso")
    void deveDepositarComSucesso() {
        ContaPoupanca conta = new ContaPoupanca("0001", cliente);
        when(contaRepository.buscarPorNumero("0001")).thenReturn(Optional.of(conta));
        when(contaRepository.salvar(any(Conta.class))).thenAnswer(invocacao -> invocacao.getArgument(0));

        Conta resultado = contaService.depositar("0001", new BigDecimal("100.00"));

        assertThat(resultado.getSaldo()).isEqualByComparingTo("100.00");
    }

    @Test
    @DisplayName("Deve sacar com sucesso")
    void deveSacarComSucesso() {
        ContaPoupanca conta = new ContaPoupanca("0001", cliente);
        conta.depositar(new BigDecimal("100.00"));
        when(contaRepository.buscarPorNumero("0001")).thenReturn(Optional.of(conta));
        when(contaRepository.salvar(any(Conta.class))).thenAnswer(invocacao -> invocacao.getArgument(0));

        Conta resultado = contaService.sacar("0001", new BigDecimal("50.00"));

        assertThat(resultado.getSaldo()).isEqualByComparingTo("50.00");
    }

    @Test
    @DisplayName("Deve lançar exceção ao sacar com saldo insuficiente")
    void deveLancarExcecaoSaldoInsuficiente() {
        ContaPoupanca conta = new ContaPoupanca("0001", cliente);
        when(contaRepository.buscarPorNumero("0001")).thenReturn(Optional.of(conta));

        assertThatThrownBy(() -> contaService.sacar("0001", new BigDecimal("50.00")))
                .isInstanceOf(SaldoInsuficienteException.class);

        verify(contaRepository, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve transferir entre contas com sucesso")
    void deveTransferirComSucesso() {
        ContaPoupanca origem = new ContaPoupanca("0001", cliente);
        origem.depositar(new BigDecimal("200.00"));
        ContaPoupanca destino = new ContaPoupanca("0002", cliente);

        when(contaRepository.buscarPorNumero("0001")).thenReturn(Optional.of(origem));
        when(contaRepository.buscarPorNumero("0002")).thenReturn(Optional.of(destino));
        when(contaRepository.salvar(any(Conta.class))).thenAnswer(invocacao -> invocacao.getArgument(0));

        contaService.transferir("0001", "0002", new BigDecimal("50.00"));

        assertThat(origem.getSaldo()).isEqualByComparingTo("150.00");
        assertThat(destino.getSaldo()).isEqualByComparingTo("50.00");
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar conta inexistente")
    void deveLancarExcecaoParaContaInexistente() {
        when(contaRepository.buscarPorNumero("9999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contaService.buscar("9999"))
                .isInstanceOf(RecursoNaoEncontradoException.class);
    }
}