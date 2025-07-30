package com.emtransporte.sistemagestao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(String to, String subject, String body) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(to);
        mensagem.setSubject(subject);
        mensagem.setText(body);
        mailSender.send(mensagem);
    }
}
