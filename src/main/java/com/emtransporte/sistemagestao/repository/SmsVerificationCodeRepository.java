package com.emtransporte.sistemagestao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emtransporte.sistemagestao.model.SmsVerificationCode;

public interface SmsVerificationCodeRepository extends JpaRepository<SmsVerificationCode, Long> {
    Optional<SmsVerificationCode> findByCpf(String cpf);
    Optional<SmsVerificationCode> findByCpfAndCodigo(String cpf, String codigo);
}
