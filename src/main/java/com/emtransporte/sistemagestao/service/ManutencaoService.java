package com.emtransporte.sistemagestao.service;

import com.emtransporte.sistemagestao.model.Manutencao;
import com.emtransporte.sistemagestao.model.Onibus;
import com.emtransporte.sistemagestao.repository.ManutencaoRepository;
import com.emtransporte.sistemagestao.repository.OnibusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ManutencaoService {

    private final ManutencaoRepository manutencaoRepository;
    private final OnibusRepository onibusRepository;

    @Autowired
    public ManutencaoService(ManutencaoRepository manutencaoRepository, OnibusRepository onibusRepository) {
        this.manutencaoRepository = manutencaoRepository;
        this.onibusRepository = onibusRepository;
    }

    public List<Manutencao> listarTodas() {
        return manutencaoRepository.findAll();
    }

    public Optional<Manutencao> buscarPorId(Long id) {
        return manutencaoRepository.findById(id);
    }

    public Manutencao salvar(Manutencao manutencao) {
        // Atualiza a quilometragem do ônibus se for uma manutenção realizada
        if (manutencao.getDataRealizada() != null && manutencao.getQuilometragemAtual() != null) {
            Onibus onibus = manutencao.getOnibus();
            if (onibus != null && manutencao.getQuilometragemAtual() > onibus.getQuilometragem()) {
                onibus.setQuilometragem(manutencao.getQuilometragemAtual());
                onibusRepository.save(onibus);
            }
        }
        return manutencaoRepository.save(manutencao);
    }

    public void deletar(Long id) {
        manutencaoRepository.deleteById(id);
    }

    public List<Manutencao> buscarPorOnibus(Long onibusId) {
        return manutencaoRepository.findByOnibusId(onibusId);
    }

    // Verifica a cada dia se algum ônibus precisa de manutenção
    @Scheduled(cron = "0 0 9 * * ?") // Executa todos os dias às 9h
    public void verificarManutencoesNecessarias() {
        List<Onibus> todosOnibus = onibusRepository.findAll();
        
        for (Onibus onibus : todosOnibus) {
            if (onibus.getQuilometragem() != null) {
                // Verifica se atingiu ou passou de 10.000 km desde a última manutenção
                if (precisaManutencao(onibus)) {
                    criarAlertaManutencao(onibus);
                }
            }
        }
    }

    private boolean precisaManutencao(Onibus onibus) {
        // Busca a última manutenção realizada para este ônibus
        List<Manutencao> manutencoes = manutencaoRepository.findByOnibusIdAndDataRealizadaIsNotNullOrderByDataRealizadaDesc(onibus.getId());
        
        if (manutencoes.isEmpty()) {
            // Se nunca teve manutenção, verifica se passou de 10.000 km
            return onibus.getQuilometragem() >= 10000;
        } else {
            Manutencao ultimaManutencao = manutencoes.get(0);
            // Verifica se já rodou mais de 10.000 km desde a última manutenção
            return (onibus.getQuilometragem() - ultimaManutencao.getQuilometragemAtual()) >= 10000;
        }
    }

    private void criarAlertaManutencao(Onibus onibus) {
        Manutencao alerta = new Manutencao();
        alerta.setDescricao("Manutenção periódica de 10.000 km necessária");
        alerta.setDataAgendada(LocalDate.now().plusDays(7)); // Sugere agendar para daqui 7 dias
        alerta.setStatus("Pendente");
        alerta.setOnibus(onibus);
        alerta.setQuilometragemAtual(onibus.getQuilometragem());
        
        manutencaoRepository.save(alerta);
    }
}