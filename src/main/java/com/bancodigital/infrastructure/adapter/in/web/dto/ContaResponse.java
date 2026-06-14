package com.bancodigital.infrastructure.adapter.in.web.dto;

import com.bancodigital.domain.model.Conta;
import com.bancodigital.domain.model.ContaCorrente;
import java.math.BigDecimal;

public record ContaResponse(
        String numero,
        String tipo,
        BigDecimal saldo,
        BigDecimal limite,
        String titularNome,
        String titularCpf
) {
    public static ContaResponse de(Conta conta) {
        return new ContaResponse(
                conta.getNumero(),
                conta instanceof ContaCorrente ? "CORRENTE" : "POUPANCA",
                conta.getSaldo(),
                conta instanceof ContaCorrente cc ? cc.getLimite() : null,
                conta.getTitular().getNome(),
                conta.getTitular().getCpf()
        );
    }
}