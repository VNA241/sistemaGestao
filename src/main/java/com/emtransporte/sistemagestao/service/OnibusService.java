package com.emtransporte.sistemagestao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    public Onibus atualizar(Long id, Onibus onibusAtualizado) {
        Optional<Onibus> onibusExistenteOptional = onibusRepository.findById(id);

        if (onibusExistenteOptional.isPresent()) {
            Onibus onibusExistente = onibusExistenteOptional.get();
            BeanUtils.copyProperties(onibusAtualizado, onibusExistente, "id");
            return onibusRepository.save(onibusExistente);
        } else {
            return null; 
        }
    }

    public Onibus registrarQuilometragem(Long id, Integer novaQuilometragem) {
        Optional<Onibus> onibusExistenteOptional = onibusRepository.findById(id);

        if (onibusExistenteOptional.isPresent()) {
            Onibus onibusExistente = onibusExistenteOptional.get();
            onibusExistente.setQuilometragem(novaQuilometragem);
            return onibusRepository.save(onibusExistente);
        } else {
            return null; 
        }
    }
}
