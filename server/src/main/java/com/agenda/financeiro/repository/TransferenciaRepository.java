package com.agenda.financeiro.repository;


import com.agenda.financeiro.domain.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {}
