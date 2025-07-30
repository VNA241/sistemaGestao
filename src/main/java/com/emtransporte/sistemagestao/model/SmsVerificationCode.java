package com.emtransporte.sistemagestao.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SmsVerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String cpf;


    private String codigo;

    private LocalDateTime expiration;

    public SmsVerificationCode() {}

    public SmsVerificationCode(String cpf, String codigo, LocalDateTime expiration) {
        this.cpf = cpf;
        this.codigo = codigo;
        this.expiration = expiration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
    
}
