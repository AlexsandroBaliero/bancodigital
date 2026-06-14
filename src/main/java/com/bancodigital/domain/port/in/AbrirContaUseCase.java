package com.bancodigital.domain.port.in;

import com.bancodigital.domain.model.Conta;

public interface AbrirContaUseCase {
    Conta abrir(String cpfTitular, String numero, String tipo, String limite);
}
