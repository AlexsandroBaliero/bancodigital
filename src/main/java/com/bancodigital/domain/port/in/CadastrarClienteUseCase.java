package com.bancodigital.domain.port.in;

import com.bancodigital.domain.model.Cliente;

public interface CadastrarClienteUseCase {
    Cliente cadastrar(Cliente cliente);
}