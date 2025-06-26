package com.emtransporte.sistemagestao.controller;

import com.emtransporte.sistemagestao.dto.AuthRequest;
import com.emtransporte.sistemagestao.model.Usuario;
import com.emtransporte.sistemagestao.repository.UsuarioRepository;
import com.emtransporte.sistemagestao.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(), request.getSenha()
                )
            );

            String token = jwtUtil.generateToken(request.getEmail());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        Optional<Usuario> exists = usuarioRepository.findByEmail(usuario.getEmail());

        if (exists.isPresent()) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuário cadastrado com sucesso");
    }
}
