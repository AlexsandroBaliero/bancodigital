package com.bancodigital.infrastructure.adapter.out.persistence;

import com.bancodigital.domain.model.Conta;
import com.bancodigital.domain.port.out.ContaRepositoryPort;
import com.bancodigital.domain.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ContaPersistenceAdapter implements ContaRepositoryPort {

    private final ContaJpaRepository jpaRepository;
    private final ClienteJpaRepository clienteJpaRepository;

    public ContaPersistenceAdapter(ContaJpaRepository jpaRepository,
                                   ClienteJpaRepository clienteJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.clienteJpaRepository = clienteJpaRepository;
    }

    @Override
    public Conta salvar(Conta conta) {
        ClienteEntity clienteEntity = clienteJpaRepository
                .findByCpf(conta.getTitular().getCpf())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Cliente não encontrado: " + conta.getTitular().getCpf()));

        // busca entidade existente pra pegar o ID (UPDATE), ou cria nova (INSERT)
        ContaEntity entity = jpaRepository.findByNumero(conta.getNumero())
                .map(existing -> {
                    // atualiza só o saldo na entidade existente
                    if (existing instanceof ContaCorrenteEntity cce) {
                        return (ContaEntity) new ContaCorrenteEntity(
                                existing.getId(), conta.getNumero(), conta.getSaldo(),
                                clienteEntity, cce.getLimite());
                    } else {
                        return (ContaEntity) new ContaPoupancaEntity(
                                existing.getId(), conta.getNumero(), conta.getSaldo(),
                                clienteEntity);
                    }
                })
                .orElseGet(() -> ContaMapper.paraEntity(conta, clienteEntity));

        ContaEntity salvo = jpaRepository.save(entity);
        return ContaMapper.paraDominio(salvo);
    }

    @Override
    public Optional<Conta> buscarPorNumero(String numero) {
        return jpaRepository.findByNumero(numero)
                .map(ContaMapper::paraDominio);
    }
}