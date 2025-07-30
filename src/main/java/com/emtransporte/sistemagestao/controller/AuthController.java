package com.emtransporte.sistemagestao.controller;

import com.emtransporte.sistemagestao.service.LoginAttemptService;
import com.emtransporte.sistemagestao.service.SmsService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emtransporte.sistemagestao.dto.AuthRequest;
import com.emtransporte.sistemagestao.dto.RecuperarSenhaRequest;
import com.emtransporte.sistemagestao.dto.RedefinirSenhaRequest;
import com.emtransporte.sistemagestao.model.PasswordResetToken;
import com.emtransporte.sistemagestao.model.Usuario;
import com.emtransporte.sistemagestao.repository.PasswordResetTokenRepository;
import com.emtransporte.sistemagestao.repository.UsuarioRepository;
import com.emtransporte.sistemagestao.security.JwtUtil;
import com.emtransporte.sistemagestao.service.EmailService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private SmsService smsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Optional<Usuario> usuarioOpt;

        if (request.getLogin() == null || request.getSenha() == null) {
            return ResponseEntity.badRequest().body("Login e senha são obrigatórios");
        }

        String login = request.getLogin();

        if (loginAttemptService.isBlocked(login)) {
            return ResponseEntity.status(429).body("Conta temporariamente bloqueada por excesso de tentativas");
        }

        if (login.contains("@")) {
            usuarioOpt = usuarioRepository.findByEmail(login);
        } else {
            usuarioOpt = usuarioRepository.findByCpf(login);
        }

        if (usuarioOpt.isEmpty()) {
            loginAttemptService.loginFailed(login);
            return ResponseEntity.status(401).body("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            loginAttemptService.loginFailed(login);
            return ResponseEntity.status(401).body("Senha inválida");
        }

        loginAttemptService.loginSucceeded(login);

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getPerfil().name());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
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

    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> solicitarRecuperacaoSenha(@RequestBody RecuperarSenhaRequest request) {
        String email = request.getEmail();
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail não encontrado");
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = LocalDateTime.now().plusHours(24);

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setEmail(email);
        resetToken.setToken(token);
        resetToken.setExpirationDate(expiracao);
        passwordResetTokenRepository.save(resetToken);

        String link = "http://localhost:8080/redefinir-senha?token=" + token;
        emailService.enviarEmail(email, "Recuperação de senha sistemaGestão", "Clique aqui para redefinir: " + link);

        return ResponseEntity.ok("E-mail de recuperação enviado com sucesso.");
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody RedefinirSenhaRequest request) {
        Optional<PasswordResetToken> tokenOptional = passwordResetTokenRepository.findByToken(request.getToken());

        if (tokenOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido");
        }

        PasswordResetToken token = tokenOptional.get();

        if (token.getExpirationDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expirado");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(token.getEmail());

        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        Usuario usuario = usuarioOptional.get();
        usuario.setSenha(passwordEncoder.encode(request.getNovaSenha()));
        usuarioRepository.save(usuario);

    
        passwordResetTokenRepository.delete(token);

        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }

    @PostMapping("/enviar-sms")
    public ResponseEntity<String> enviarCodigoSms(@RequestBody Map<String, String> body) {
        String cpf = body.get("cpf");
        Optional<Usuario> user = usuarioRepository.findByCpf(cpf);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        smsService.enviarCodigoSms(cpf);
        return ResponseEntity.ok("Código SMS enviado com sucesso");
    }

    @PostMapping("/redefinir-senha-sms")
    public ResponseEntity<String> redefinirSenhaViaSms(@RequestBody Map<String, String> body) {
        String cpf = body.get("cpf");
        String codigo = body.get("codigo");
        String novaSenha = body.get("novaSenha");

        if (!smsService.verificarCodigo(cpf, codigo)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código inválido ou expirado");
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findByCpf(cpf);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        smsService.removerCodigo(cpf);

        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }



}
