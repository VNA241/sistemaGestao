package com.emtransporte.sistemagestao.controller;

import com.emtransporte.sistemagestao.model.Motorista;
import com.emtransporte.sistemagestao.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/motoristas")
public class MotoristaController {

    @Autowired
    private MotoristaService motoristaService;

    @GetMapping
    public List<Motorista> listarTodos() {
        return motoristaService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Motorista> buscarPorId(@PathVariable Long id) {
        return motoristaService.buscarPorId(id);
    }

    @PostMapping
    public Motorista salvar(@RequestBody Motorista motorista) {
        return motoristaService.salvar(motorista);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        motoristaService.deletar(id);
    }
}
