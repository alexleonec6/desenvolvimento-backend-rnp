package com.alexandre.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Modulo 2 do curso:
// Essa anotacao @SpringBootApplication e a principal do Spring Boot.
// Ela junta 3 coisas:
// 1. @Configuration: indica que a classe pode definir beans de configuracao.
// 2. @EnableAutoConfiguration: liga a configuracao automatica do Spring com base nos pacotes do pom.xml.
// 3. @ComponentScan: varre todas as classes dessa pasta para achar @RestController, @Service, @Repository.
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        System.out.println("Iniciando a API REST do curso de Desenvolvimento Backend da RNP...");
        SpringApplication.run(BackendApplication.class, args);
        System.out.println("API REST rodando com sucesso na porta 8080!");
    }
}
