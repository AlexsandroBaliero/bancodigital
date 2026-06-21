package com.bancodigital.application.service;

import com.bancodigital.domain.exception.RecursoNaoEncontradoException;
import com.bancodigital.domain.model.Cliente;
import com.bancodigital.domain.port.out.ClienteRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ClienteService")
class ClienteServiceTest {

    @Mock
    private ClienteRepositoryPort clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    @DisplayName("Deve cadastrar cliente com sucesso")
    void deveCadastrarClienteComSucesso() {
        Cliente cliente = new Cliente("Alex", "12345678901");

        when(clienteRepository.buscarPorCpf("12345678901")).thenReturn(Optional.empty());
        when(clienteRepository.salvar(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.cadastrar(cliente);

        assertThat(resultado.getNome()).isEqualTo("Alex");
        verify(clienteRepository).salvar(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção para CPF duplicado")
    void deveLancarExcecaoParaCpfDuplicado() {
        Cliente clienteExistente = new Cliente("Outro", "12345678901");
        when(clienteRepository.buscarPorCpf("12345678901")).thenReturn(Optional.of(clienteExistente));

        Cliente novoCliente = new Cliente("Alex", "12345678901");

        assertThatThrownBy(() -> clienteService.cadastrar(novoCliente))
                .isInstanceOf(RecursoNaoEncontradoException.class);

        verify(clienteRepository, never()).salvar(any());
    }
}