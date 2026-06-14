package com.bancodigital.domain.port.out;

import com.bancodigital.domain.model.Conta;
import java.util.Optional;

public interface ContaRepositoryPort {
    Conta salvar(Conta conta);
    Optional<Conta> buscarPorNumero(String numero);
}