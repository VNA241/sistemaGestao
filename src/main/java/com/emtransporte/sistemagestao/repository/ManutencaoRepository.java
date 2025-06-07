package com.emtransporte.sistemagestao.repository;

import com.emtransporte.sistemagestao.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
}
