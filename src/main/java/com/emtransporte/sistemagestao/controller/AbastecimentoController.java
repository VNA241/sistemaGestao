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

import com.emtransporte.sistemagestao.model.Abastecimento;
import com.emtransporte.sistemagestao.service.AbastecimentoService;


@RestController
@RequestMapping("/abastecimentos")
public class AbastecimentoController {

    private final AbastecimentoService abastecimentoService;

    public AbastecimentoController( AbastecimentoService abastecimentoService) {
        this.abastecimentoService = abastecimentoService;
    }

    @GetMapping
    public List<Abastecimento> listarTodos() {
        return abastecimentoService.listarTodos();
    }

    @PostMapping("/{id}")
    public Abastecimento salvar(@PathVariable Long id, @RequestBody Abastecimento abastecimento) {
        return abastecimentoService.salvar(id, abastecimento);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        abastecimentoService.deletar(id);
    }

    @PutMapping("/{id}")
    public Abastecimento atualizar(@PathVariable Long id, @RequestBody Abastecimento abastecimentoAtualizado) {
        return abastecimentoService.atualizar(id, abastecimentoAtualizado);
    }

    @PostMapping("/{id}/abastecimentos")
    public Abastecimento registrarAbastecimento(@PathVariable Long id, @RequestBody Abastecimento novoAbastecimento) {
        return abastecimentoService.salvar(id, novoAbastecimento);
    }
}