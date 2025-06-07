package com.emtransporte.sistemagestao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emtransporte.sistemagestao.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Podemos colocar métodos personalizados depois
}

