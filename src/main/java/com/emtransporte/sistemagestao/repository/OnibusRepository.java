package com.emtransporte.sistemagestao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.emtransporte.sistemagestao.model.Onibus;

public interface OnibusRepository extends JpaRepository<Onibus, Long> {
}