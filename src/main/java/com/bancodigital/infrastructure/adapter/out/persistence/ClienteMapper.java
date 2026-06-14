package com.bancodigital.infrastructure.adapter.out.persistence;

import com.bancodigital.domain.model.Cliente;

public class ClienteMapper {

    public static ClienteEntity paraEntity(Cliente cliente) {
        return new ClienteEntity(cliente.getNome(), cliente.getCpf());
    }

    public static Cliente paraDominio(ClienteEntity entity) {
        return new Cliente(entity.getNome(), entity.getCpf());
    }
}