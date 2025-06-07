package com.emtransporte.sistemagestao.repository;

import com.emtransporte.sistemagestao.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long> {
}

