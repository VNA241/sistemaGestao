package com.emtransporte.sistemagestao.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.emtransporte.sistemagestao.model.Onibus;
import com.emtransporte.sistemagestao.service.OnibusService;

@RestController
@RequestMapping("/onibus")
public class OnibusController {

    private final OnibusService onibusService;

    public OnibusController(OnibusService onibusService) {
        this.onibusService = onibusService;
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
}
