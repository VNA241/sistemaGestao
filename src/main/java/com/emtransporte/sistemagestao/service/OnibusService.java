package com.emtransporte.sistemagestao.service;

import org.springframework.stereotype.Service;
import java.util.List;

import com.emtransporte.sistemagestao.model.Onibus;
import com.emtransporte.sistemagestao.repository.OnibusRepository;

@Service
public class OnibusService {

    private final OnibusRepository onibusRepository;

    public OnibusService(OnibusRepository onibusRepository) {
        this.onibusRepository = onibusRepository;
    }

    public List<Onibus> listarTodos() {
        return onibusRepository.findAll();
    }

    public Onibus salvar(Onibus onibus) {
        return onibusRepository.save(onibus);
    }

    public void deletar(Long id) {
        onibusRepository.deleteById(id);
    }
}
