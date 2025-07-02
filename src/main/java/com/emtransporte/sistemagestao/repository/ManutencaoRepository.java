package com.emtransporte.sistemagestao.repository;

import com.emtransporte.sistemagestao.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    List<Manutencao> findByOnibusId(Long onibusId);
    List<Manutencao> findByOnibusIdAndDataRealizadaIsNotNullOrderByDataRealizadaDesc(Long onibusId);
}