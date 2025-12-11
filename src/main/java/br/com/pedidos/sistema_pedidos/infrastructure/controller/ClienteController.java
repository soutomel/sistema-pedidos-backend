package br.com.pedidos.sistema_pedidos.infrastructure.controller;

import br.com.pedidos.sistema_pedidos.application.usecase.CriarClienteUseCase;
import br.com.pedidos.sistema_pedidos.domain.model.Cliente;
import br.com.pedidos.sistema_pedidos.application.mapper.ClienteMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final CriarClienteUseCase criarClienteUseCase;

    public ClienteController(CriarClienteUseCase criarClienteUseCase) {
        this.criarClienteUseCase = criarClienteUseCase;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> criarCliente(@RequestBody ClienteRequest request) {

        Cliente novoCliente = ClienteMapper.toDomain(request);

        Cliente clienteSalvo = criarClienteUseCase.executar(novoCliente);

        ClienteResponse response = new ClienteResponse(clienteSalvo.getCpf(), clienteSalvo.getNome());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    private record ClienteResponse(String cpf, String nome) {}
}