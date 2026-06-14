package com.bancodigital.infrastructure.adapter.out.persistence;

import com.bancodigital.domain.model.*;
import java.math.BigDecimal;

public class ContaMapper {

    public static ContaEntity paraEntity(Conta conta, ClienteEntity titularEntity) {
        if (conta instanceof ContaCorrente cc) {
            return new ContaCorrenteEntity(
                    conta.getNumero(), conta.getSaldo(), titularEntity, cc.getLimite());
        } else {
            return new ContaPoupancaEntity(
                    conta.getNumero(), conta.getSaldo(), titularEntity);
        }
    }

    public static Conta paraDominio(ContaEntity entity) {
        Cliente titular = ClienteMapper.paraDominio(entity.getTitular());

        if (entity instanceof ContaCorrenteEntity cce) {
            ContaCorrente cc = new ContaCorrente(
                    entity.getNumero(), titular, cce.getLimite());
            ajustarSaldo(cc, entity.getSaldo());
            return cc;
        } else {
            ContaPoupanca cp = new ContaPoupanca(entity.getNumero(), titular);
            ajustarSaldo(cp, entity.getSaldo());
            return cp;
        }
    }

    private static void ajustarSaldo(Conta conta, BigDecimal saldo) {
        if (saldo.compareTo(BigDecimal.ZERO) > 0) {
            conta.depositar(saldo);
        }
    }
}