package com.bancodigital.domain.port.out;

import com.bancodigital.domain.model.Cliente;
import java.util.Optional;

public interface ClienteRepositoryPort {
    Cliente salvar(Cliente cliente);
    Optional<Cliente> buscarPorCpf(String cpf);
}
