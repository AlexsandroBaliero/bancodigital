package com.bancodigital.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record AbrirContaRequest(
        @NotBlank String cpfTitular,
        @NotBlank String numero,
        @NotBlank String tipo,
        String limite
) {}