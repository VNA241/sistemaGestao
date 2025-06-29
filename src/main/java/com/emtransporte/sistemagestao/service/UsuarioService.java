package com.emtransporte.sistemagestao.service;

import com.emtransporte.sistemagestao.dto.UsuarioAtualizacaoDTO;
import com.emtransporte.sistemagestao.model.Usuario;
import com.emtransporte.sistemagestao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario adicionarUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Long id, UsuarioAtualizacaoDTO dto) {
        return usuarioRepository.findById(id).map(usuario -> {
            if (dto.getNome() != null) {
                usuario.setNome(dto.getNome());
            }
            if (dto.getEmail() != null) {
                usuario.setEmail(dto.getEmail());
            }
            if (dto.getSenha() != null) {
                usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
            }
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }

    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
