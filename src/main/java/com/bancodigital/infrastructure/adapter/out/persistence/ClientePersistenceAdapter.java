package com.bancodigital.infrastructure.adapter.out.persistence;

import com.bancodigital.domain.model.Cliente;
import com.bancodigital.domain.port.out.ClienteRepositoryPort;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ClientePersistenceAdapter implements ClienteRepositoryPort {

    private final ClienteJpaRepository jpaRepository;

    public ClientePersistenceAdapter(ClienteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        ClienteEntity entity = ClienteMapper.paraEntity(cliente);
        ClienteEntity salvo = jpaRepository.save(entity);
        return ClienteMapper.paraDominio(salvo);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return jpaRepository.findByCpf(cpf).map(ClienteMapper::paraDominio);
    }
}