package com.agenda.financeiro;

import com.agenda.financeiro.domain.Transferencia;
import com.agenda.financeiro.repository.TransferenciaRepository;
import com.agenda.financeiro.service.TransferenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransferenciaServiceTest {

	@Mock
	private TransferenciaRepository repository;

	@InjectMocks
	private TransferenciaService service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveCalcularTaxaParaHoje() {
		Transferencia transferencia = new Transferencia();
		transferencia.setValor(1000.0);
		transferencia.setDataTransferencia(LocalDate.now());

		when(repository.save(any(Transferencia.class))).thenReturn(transferencia);

		Transferencia resultado = service.agendarTransferencia(transferencia);

		assertEquals(3.00 + (1000.0 * 0.025), resultado.getTaxa());
		verify(repository, times(1)).save(any(Transferencia.class));
	}

	@Test
	void deveCalcularTaxaPara1A10Dias() {
		Transferencia transferencia = new Transferencia();
		transferencia.setValor(1000.0);
		transferencia.setDataTransferencia(LocalDate.now().plusDays(5));

		when(repository.save(any(Transferencia.class))).thenReturn(transferencia);

		Transferencia resultado = service.agendarTransferencia(transferencia);

		assertEquals(12.00, resultado.getTaxa());
		verify(repository, times(1)).save(any(Transferencia.class));
	}

	@Test
	void deveCalcularTaxaPara11A20Dias() {
		Transferencia transferencia = new Transferencia();
		transferencia.setValor(1000.0);
		transferencia.setDataTransferencia(LocalDate.now().plusDays(15));

		when(repository.save(any(Transferencia.class))).thenReturn(transferencia);

		Transferencia resultado = service.agendarTransferencia(transferencia);

		assertEquals(1000.0 * 0.082, resultado.getTaxa());
		verify(repository, times(1)).save(any(Transferencia.class));
	}

	@Test
	void deveCalcularTaxaPara21A30Dias() {
		Transferencia transferencia = new Transferencia();
		transferencia.setValor(1000.0);
		transferencia.setDataTransferencia(LocalDate.now().plusDays(25));

		when(repository.save(any(Transferencia.class))).thenReturn(transferencia);

		Transferencia resultado = service.agendarTransferencia(transferencia);

		assertEquals(1000.0 * 0.069, resultado.getTaxa());
		verify(repository, times(1)).save(any(Transferencia.class));
	}

	@Test
	void deveLancarExcecaoParaMaisDe50Dias() {
		Transferencia transferencia = new Transferencia();
		transferencia.setValor(1000.0);
		transferencia.setDataTransferencia(LocalDate.now().plusDays(60));

		try {
			service.agendarTransferencia(transferencia);
		} catch (IllegalArgumentException e) {
			assertEquals("Data de transferência fora do intervalo permitido (até 50 dias)", e.getMessage());
		}

		verify(repository, never()).save(any(Transferencia.class));
	}
}