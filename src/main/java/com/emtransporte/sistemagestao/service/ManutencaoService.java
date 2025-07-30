package com.emtransporte.sistemagestao.service;

import com.emtransporte.sistemagestao.model.Manutencao;
import com.emtransporte.sistemagestao.repository.ManutencaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManutencaoService {

    @Autowired
    private ManutencaoRepository manutencaoRepository;

    public List<Manutencao> listarTodas() {
        return manutencaoRepository.findAll();
    }

    public Optional<Manutencao> buscarPorId(Long id) {
        return manutencaoRepository.findById(id);
    }

    public Manutencao salvar(Manutencao manutencao) {
        return manutencaoRepository.save(manutencao);
    }

    public void deletar(Long id) {
        manutencaoRepository.deleteById(id);
    }
}
