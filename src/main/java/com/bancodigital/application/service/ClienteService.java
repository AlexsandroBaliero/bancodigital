package com.bancodigital.application.service;

import com.bancodigital.domain.exception.RecursoNaoEncontradoException;
import com.bancodigital.domain.model.Cliente;
import com.bancodigital.domain.port.in.CadastrarClienteUseCase;
import com.bancodigital.domain.port.out.ClienteRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements CadastrarClienteUseCase {

    private final ClienteRepositoryPort clienteRepository;

    public ClienteService(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente cadastrar(Cliente cliente) {
        clienteRepository.buscarPorCpf(cliente.getCpf())
                .ifPresent(c -> {
                    throw new RecursoNaoEncontradoException("CPF já cadastrado: " + cliente.getCpf());
                });
        return clienteRepository.salvar(cliente);
    }
}