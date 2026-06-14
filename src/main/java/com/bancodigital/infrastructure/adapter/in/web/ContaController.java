package com.bancodigital.infrastructure.adapter.in.web;

import com.bancodigital.domain.model.Conta;
import com.bancodigital.domain.port.in.AbrirContaUseCase;
import com.bancodigital.domain.port.in.OperacaoContaUseCase;
import com.bancodigital.infrastructure.adapter.in.web.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final AbrirContaUseCase abrirConta;
    private final OperacaoContaUseCase operacaoConta;

    public ContaController(AbrirContaUseCase abrirConta,
                           OperacaoContaUseCase operacaoConta) {
        this.abrirConta = abrirConta;
        this.operacaoConta = operacaoConta;
    }

    @PostMapping
    public ResponseEntity<ContaResponse> abrir(@Valid @RequestBody AbrirContaRequest request) {
        Conta conta = abrirConta.abrir(
                request.cpfTitular(), request.numero(), request.tipo(), request.limite());
        return ResponseEntity
                .created(URI.create("/contas/" + conta.getNumero()))
                .body(ContaResponse.de(conta));
    }

    @GetMapping("/{numero}")
    public ResponseEntity<ContaResponse> buscar(@PathVariable String numero) {
        return ResponseEntity.ok(ContaResponse.de(operacaoConta.buscar(numero)));
    }

    @PostMapping("/{numero}/depositar")
    public ResponseEntity<ContaResponse> depositar(
            @PathVariable String numero,
            @Valid @RequestBody OperacaoRequest request) {
        return ResponseEntity.ok(ContaResponse.de(
                operacaoConta.depositar(numero, request.valor())));
    }

    @PostMapping("/{numero}/sacar")
    public ResponseEntity<ContaResponse> sacar(
            @PathVariable String numero,
            @Valid @RequestBody OperacaoRequest request) {
        return ResponseEntity.ok(ContaResponse.de(
                operacaoConta.sacar(numero, request.valor())));
    }

    @PostMapping("/{numero}/transferir")
    public ResponseEntity<ContaResponse> transferir(
            @PathVariable String numero,
            @Valid @RequestBody TransferenciaRequest request) {
        return ResponseEntity.ok(ContaResponse.de(
                operacaoConta.transferir(numero, request.numeroDestino(), request.valor())));
    }
}