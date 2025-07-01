package com.emtransporte.sistemagestao.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Abastecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "onibus_id")
    private Onibus onibus;

    private Date data;
    private Double litros;
    private Double valor;

    public Abastecimento() { 
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Onibus getOnibus() {
        return onibus;
    }
    public void setOnibus(Onibus onibus) {
        this.onibus = onibus;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public Double getLitros() {
        return litros;
    }
    public void setLitros(Double litros) {
        this.litros = litros;
    }
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
}

