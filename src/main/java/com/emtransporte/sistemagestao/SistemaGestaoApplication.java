package com.emtransporte.sistemagestao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SistemaGestaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaGestaoApplication.class, args);
	}

}
