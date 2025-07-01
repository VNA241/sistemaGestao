package com.emtransporte.sistemagestao.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emtransporte.sistemagestao.model.Onibus;
import com.emtransporte.sistemagestao.model.Abastecimento;
import com.emtransporte.sistemagestao.service.AbastecimentoService;
import com.emtransporte.sistemagestao.service.OnibusService;

@RestController
@RequestMapping("/onibus")
public class OnibusController {

    private final OnibusService onibusService;
    private final AbastecimentoService abastecimentoService;

    public OnibusController(OnibusService onibusService, AbastecimentoService abastecimentoService) {
        this.onibusService = onibusService;
        this.abastecimentoService = abastecimentoService;
    }

    @GetMapping
    public List<Onibus> listarTodos() {
        return onibusService.listarTodos();
    }

    @PostMapping
    public Onibus salvar(@RequestBody Onibus onibus) {
        return onibusService.salvar(onibus);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        onibusService.deletar(id);
    }

    @PutMapping("/{id}")
    public Onibus atualizar(@PathVariable Long id, @RequestBody Onibus onibusAtualizado) {
    return onibusService.atualizar(id, onibusAtualizado);
    }

    @PutMapping("/{id}/quilometragem")
    public Onibus registrarQuilometragem(@PathVariable Long id, @RequestBody Integer novaQuilometragem) {
    return onibusService.registrarQuilometragem(id, novaQuilometragem);
    }

    @PostMapping("/{id}/abastecimentos")
    public Abastecimento registrarAbastecimento(@PathVariable Long id, @RequestBody Abastecimento novoAbastecimento) {
        return abastecimentoService.salvar(id, novoAbastecimento);
    }
}
