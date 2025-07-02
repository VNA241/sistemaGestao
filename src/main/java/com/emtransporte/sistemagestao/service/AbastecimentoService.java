package com.emtransporte.sistemagestao.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.emtransporte.sistemagestao.model.Abastecimento;
import com.emtransporte.sistemagestao.model.Onibus;
import com.emtransporte.sistemagestao.repository.AbastecimentoRepository;
import com.emtransporte.sistemagestao.repository.OnibusRepository;

@Service
public class AbastecimentoService {

    private final AbastecimentoRepository abastecimentoRepository;
    private final OnibusRepository onibusRepository; 

    public AbastecimentoService(AbastecimentoRepository abastecimentoRepository, OnibusRepository onibusRepository) {
        this.abastecimentoRepository = abastecimentoRepository;
        this.onibusRepository = onibusRepository;
    }

    public Abastecimento salvar(Long idOnibus, Abastecimento abastecimento) {
        Optional<Onibus> onibusOptional = onibusRepository.findById(idOnibus);

        if (onibusOptional.isPresent()) {
            Onibus onibus = onibusOptional.get();
            abastecimento.setOnibus(onibus); // Define a relação com o ônibus
            return abastecimentoRepository.save(abastecimento);
        } else {
            return null; 
        }
    }

    public List<Abastecimento> listarTodos() {
        return abastecimentoRepository.findAll();
    }

    public void deletar(Long id) {
        abastecimentoRepository.deleteById(id);
    }

    public Abastecimento buscarPorId(Long id) {
        return abastecimentoRepository.findById(id).orElse(null); 
    }

    public List<Abastecimento> buscarPorOnibus(Long idOnibus) {
        return abastecimentoRepository.findByOnibusId(idOnibus); 
    }

    public List<Abastecimento> buscarPorData(Date data) {
        return abastecimentoRepository.findByData(data); 
    }

    public List<Abastecimento> buscarPorOnibusData(Long idOnibus, Date data) {
        return abastecimentoRepository.findByOnibusIdAndData(idOnibus, data); 
    }

    
    public Abastecimento atualizar(Long id, Abastecimento abastecimentoAtualizado) {
        Abastecimento abastecimento = buscarPorId(id);
        if (abastecimento != null) {
            abastecimento.setData(abastecimentoAtualizado.getData());
            abastecimento.setLitros(abastecimentoAtualizado.getLitros());
            abastecimento.setCusto(abastecimentoAtualizado.getCusto());
            return abastecimentoRepository.save(abastecimento);
        } else {
            return null; 
        }
    }
}
