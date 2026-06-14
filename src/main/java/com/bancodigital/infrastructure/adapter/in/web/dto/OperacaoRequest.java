package com.bancodigital.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record OperacaoRequest(
        @NotNull @Positive(message = "Valor deve ser positivo")
        BigDecimal valor
) {}