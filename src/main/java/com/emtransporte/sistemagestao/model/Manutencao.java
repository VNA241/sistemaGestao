package com.emtransporte.sistemagestao.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "manutencao") // Opcional - define o nome da tabela no banco
public class Manutencao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Column(name = "data_agendada", columnDefinition = "DATE")
    private LocalDate dataAgendada;

    @Column(name = "data_realizada", columnDefinition = "DATE")
    private LocalDate dataRealizada;

    private String status;

    @ElementCollection
    @CollectionTable(name = "manutencao_pecas", joinColumns = @JoinColumn(name = "manutencao_id"))
    @Column(name = "peca")
    private List<String> pecasSubstituidas;

    @Column(name = "custo_pecas")
    private Double custoPecas;

    @Column(name = "custo_mao_obra")
    private Double custoMaoDeObra;

    @Column(name = "custo_total")
    private Double custoTotal;

    @Column(name = "quilometragem_atual")
    private Integer quilometragemAtual;

    @ManyToOne
    @JoinColumn(name = "onibus_id")
    private Onibus onibus;

    public List<String> getPecasSubstituidas() {
        return pecasSubstituidas;
    }

    public void setPecasSubstituidas(List<String> pecasSubstituidas) {
        this.pecasSubstituidas = pecasSubstituidas;
    }

    public Double getCustoPecas() {
        return custoPecas;
    }

    public void setCustoPecas(Double custoPecas) {
        this.custoPecas = custoPecas;
        calcularCustoTotal();
    }

    public Double getCustoMaoDeObra() {
        return custoMaoDeObra;
    }

    public void setCustoMaoDeObra(Double custoMaoDeObra) {
        this.custoMaoDeObra = custoMaoDeObra;
        calcularCustoTotal();
    }

    public Double getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(Double custoTotal) {
        this.custoTotal = custoTotal;
    }

    public Integer getQuilometragemAtual() {
        return quilometragemAtual;
    }

    public void setQuilometragemAtual(Integer quilometragemAtual) {
        this.quilometragemAtual = quilometragemAtual;
    }

    private void calcularCustoTotal() {
        if (custoPecas != null && custoMaoDeObra != null) {
            this.custoTotal = custoPecas + custoMaoDeObra;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataAgendada() {
        return dataAgendada;
    }

    public void setDataAgendada(LocalDate dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public LocalDate getDataRealizada() {
        return dataRealizada;
    }

    public void setDataRealizada(LocalDate dataRealizada) {
        this.dataRealizada = dataRealizada;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Onibus getOnibus() {
        return onibus;
    }

    public void setOnibus(Onibus onibus) {
        this.onibus = onibus;
    }

    @PrePersist
    @PreUpdate
    private void validarDatas() {
        if (dataRealizada != null && dataAgendada != null && dataRealizada.isBefore(dataAgendada)) {
            throw new IllegalArgumentException("Data realizada não pode ser anterior à data agendada");
        }

        if (dataRealizada != null && dataRealizada.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data realizada não pode ser no futuro");
        }
    }
}