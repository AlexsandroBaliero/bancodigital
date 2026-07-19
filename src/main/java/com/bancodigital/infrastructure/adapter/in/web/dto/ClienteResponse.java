package com.bancodigital.infrastructure.adapter.in.web.dto;

import com.bancodigital.domain.model.Cliente;

public record ClienteResponse(
        String nome,
        String cpf
) {
    public static ClienteResponse de(Cliente cliente) {
        return new ClienteResponse(
                cliente.getNome(),
                cliente.getCpf()
        );
    }
}