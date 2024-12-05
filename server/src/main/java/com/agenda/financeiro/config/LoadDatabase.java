package com.agenda.financeiro.config;

import com.agenda.financeiro.domain.Transferencia;
import com.agenda.financeiro.repository.TransferenciaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(TransferenciaRepository repository) {
        return args -> {
            repository.save(new Transferencia(
                    null,
                    "1",
                    "2",
                    1000.00,
                    15.00,
                    LocalDate.now(),
                    LocalDate.now().plusDays(5)
            ));

            repository.save(new Transferencia(
                    null,
                    "2",
                    "3",
                    500.00,
                    15.00,
                    LocalDate.now(),
                    LocalDate.now().plusDays(10)
            ));

            System.out.println("Banco de dados inicializado com dados de teste.");
        };
    }
}
