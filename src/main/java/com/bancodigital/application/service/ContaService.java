package com.bancodigital.application.service;

import com.bancodigital.domain.exception.RecursoNaoEncontradoException;
import com.bancodigital.domain.model.*;
import com.bancodigital.domain.port.in.AbrirContaUseCase;
import com.bancodigital.domain.port.in.OperacaoContaUseCase;
import com.bancodigital.domain.port.out.ClienteRepositoryPort;
import com.bancodigital.domain.port.out.ContaRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
public class ContaService implements AbrirContaUseCase, OperacaoContaUseCase {

    private final ContaRepositoryPort contaRepository;
    private final ClienteRepositoryPort clienteRepository;

    public ContaService(ContaRepositoryPort contaRepository,
                        ClienteRepositoryPort clienteRepository) {
        this.contaRepository = contaRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Conta abrir(String cpfTitular, String numero, String tipo, String limite) {
        Cliente titular = clienteRepository.buscarPorCpf(cpfTitular)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado: " + cpfTitular));

        Conta conta = switch (tipo.toUpperCase()) {
            case "CORRENTE" -> new ContaCorrente(numero, titular,
                    new BigDecimal(limite != null ? limite : "0"));
            case "POUPANCA" -> new ContaPoupanca(numero, titular);
            default -> throw new RecursoNaoEncontradoException("Tipo inválido: " + tipo);
        };

        return contaRepository.salvar(conta);
    }

    @Override
    public Conta depositar(String numero, BigDecimal valor) {
        Conta conta = buscar(numero);
        conta.depositar(valor);
        return contaRepository.salvar(conta);
    }

    @Override
    public Conta sacar(String numero, BigDecimal valor) {
        Conta conta = buscar(numero);
        conta.sacar(valor);
        return contaRepository.salvar(conta);
    }

    @Override
    public Conta transferir(String numeroOrigem, String numeroDestino, BigDecimal valor) {
        Conta origem = buscar(numeroOrigem);
        Conta destino = buscar(numeroDestino);
        origem.sacar(valor);
        destino.depositar(valor);
        contaRepository.salvar(origem);
        contaRepository.salvar(destino);
        return origem;
    }

    @Override
    public Conta buscar(String numero) {
        return contaRepository.buscarPorNumero(numero)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada: " + numero));
    }
}