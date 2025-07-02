package com.emtransporte.sistemagestao.controller;

import com.emtransporte.sistemagestao.model.Manutencao;
import com.emtransporte.sistemagestao.service.ManutencaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manutencoes")
public class ManutencaoController {

    @Autowired
    private ManutencaoService manutencaoService;

    @GetMapping
    public List<Manutencao> listarTodas() {
        return manutencaoService.listarTodas();
    }

    @GetMapping("/{id}")
    public Optional<Manutencao> buscarPorId(@PathVariable Long id) {
        return manutencaoService.buscarPorId(id);
    }

    @PostMapping
    public Manutencao criar(@RequestBody Manutencao manutencao) {
        return manutencaoService.salvar(manutencao);
    }

    @PutMapping("/{id}")
    public Manutencao atualizar(@PathVariable Long id, @RequestBody Manutencao manutencao) {
        manutencao.setId(id);
        return manutencaoService.salvar(manutencao);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        manutencaoService.deletar(id);
    }

    @Autowired
    public ManutencaoController(ManutencaoService manutencaoService) {
        this.manutencaoService = manutencaoService;
    }
    @GetMapping("/onibus/{onibusId}")
    public List<Manutencao> buscarPorOnibus(@PathVariable Long onibusId) {
        return manutencaoService.buscarPorOnibus(onibusId);
    }

    @PutMapping("/{id}/realizar")
    public Manutencao realizarManutencao(
            @PathVariable Long id,
            @RequestParam Integer quilometragem,
            @RequestBody List<String> pecasSubstituidas,
            @RequestParam Double custoPecas,
            @RequestParam Double custoMaoDeObra) {
        
        Optional<Manutencao> manutencaoOptional = manutencaoService.buscarPorId(id);
        if (manutencaoOptional.isPresent()) {
            Manutencao manutencao = manutencaoOptional.get();
            manutencao.setDataRealizada(LocalDate.now());
            manutencao.setStatus("Concluída");
            manutencao.setQuilometragemAtual(quilometragem);
            manutencao.setPecasSubstituidas(pecasSubstituidas);
            manutencao.setCustoPecas(custoPecas);
            manutencao.setCustoMaoDeObra(custoMaoDeObra);
            
            return manutencaoService.salvar(manutencao);
        }
        return null;
    }

}

