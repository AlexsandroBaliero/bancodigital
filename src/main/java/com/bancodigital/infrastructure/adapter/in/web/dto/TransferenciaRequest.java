package com.bancodigital.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record TransferenciaRequest(
        @NotBlank String numeroDestino,
        @NotNull @Positive BigDecimal valor
) {}