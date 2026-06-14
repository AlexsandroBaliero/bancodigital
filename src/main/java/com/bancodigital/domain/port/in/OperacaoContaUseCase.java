package com.bancodigital.domain.port.in;

import com.bancodigital.domain.model.Conta;
import java.math.BigDecimal;

public interface OperacaoContaUseCase {
    Conta depositar(String numero, BigDecimal valor);
    Conta sacar(String numero, BigDecimal valor);
    Conta transferir(String numeroOrigem, String numeroDestino, BigDecimal valor);
    Conta buscar(String numero);
}