package com.emtransporte.sistemagestao.service;

import com.emtransporte.sistemagestao.model.Motorista;
import com.emtransporte.sistemagestao.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotoristaService {

    @Autowired
    private MotoristaRepository motoristaRepository;

    public List<Motorista> listarTodos() {
        return motoristaRepository.findAll();
    }

    public Optional<Motorista> buscarPorId(Long id) {
        return motoristaRepository.findById(id);
    }

    public Motorista salvar(Motorista motorista) {
        return motoristaRepository.save(motorista);
    }

    public void deletar(Long id) {
        motoristaRepository.deleteById(id);
    }
}