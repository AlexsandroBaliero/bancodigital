package com.bancodigital.infrastructure.adapter.in.web;
import com.bancodigital.domain.model.Cliente;
import com.bancodigital.domain.port.in.CadastrarClienteUseCase;
import com.bancodigital.infrastructure.adapter.in.web.dto.CadastrarClienteRequest;
import com.bancodigital.infrastructure.adapter.in.web.dto.ClienteResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final CadastrarClienteUseCase cadastrarCliente;

    public ClienteController(CadastrarClienteUseCase cadastrarCliente) {
        this.cadastrarCliente = cadastrarCliente;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@Valid @RequestBody CadastrarClienteRequest request) {
        Cliente novo = new Cliente(request.nome(), request.cpf());
        Cliente salvo = cadastrarCliente.cadastrar(novo);
        return ResponseEntity
                .created(URI.create("/clientes/" + salvo.getCpf()))
                .body(ClienteResponse.de(salvo));

    }
}