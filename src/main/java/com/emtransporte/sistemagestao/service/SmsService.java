package com.emtransporte.sistemagestao.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emtransporte.sistemagestao.model.SmsVerificationCode;
import com.emtransporte.sistemagestao.repository.SmsVerificationCodeRepository;

@Service
public class SmsService {

    @Autowired
    private SmsVerificationCodeRepository smsRepo;

    public void enviarCodigoSms(String cpf) {
        String codigo = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiracao = LocalDateTime.now().plusMinutes(5);

        SmsVerificationCode sms = new SmsVerificationCode(cpf, codigo, expiracao);
        smsRepo.save(sms);

        // Aqui era pra colocar o Twillio ou alguma outra API, mas depois que vi que tinha que pagar...
        System.out.println("Código enviado por SMS para CPF " + cpf + ": " + codigo);
    }

    public boolean verificarCodigo(String cpf, String codigo) {
        return smsRepo.findByCpfAndCodigo(cpf, codigo)
                .filter(s -> s.getExpiration().isAfter(LocalDateTime.now()))
                .isPresent();
    }

    public void removerCodigo(String cpf) {
        smsRepo.findByCpf(cpf).ifPresent(smsRepo::delete);
    }
}
