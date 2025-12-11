package br.com.pedidos.sistema_pedidos.infrastructure.controller;

import br.com.pedidos.sistema_pedidos.infrastructure.dto.LoginDTO;
import br.com.pedidos.sistema_pedidos.infrastructure.dto.RegisterDTO;
import br.com.pedidos.sistema_pedidos.infrastructure.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // habilita chamadas do frontend
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {

        // Aqui você faria verificação real (banco, senha hash etc.)
        // Neste momento vamos só simular

        if (dto.getEmail() == null || dto.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email or password missing");
        }

        UserResponse user = new UserResponse(
                "1",
                "Usuário " + dto.getEmail().split("@")[0],
                dto.getEmail(),
                "00000000000"
        );

        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO dto) {

        if (dto.getName() == null || dto.getEmail() == null || dto.getCpf() == null) {
            return ResponseEntity.badRequest().body("Missing fields");
        }

        UserResponse user = new UserResponse(
                "2",
                dto.getName(),
                dto.getEmail(),
                dto.getCpf()
        );

        return ResponseEntity.ok(user);
    }
}
