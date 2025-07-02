package com.emtransporte.sistemagestao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emtransporte.sistemagestao.model.Abastecimento;

public interface AbastecimentoRepository extends JpaRepository<Abastecimento, Long> {
    List<Abastecimento> findByData(Date data);
    List<Abastecimento> findByOnibusIdAndData(Long idOnibus, Date data);
    List<Abastecimento> findByOnibusId(Long idOnibus);
}