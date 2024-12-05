package com.agenda.financeiro.service;

import com.agenda.financeiro.domain.Transferencia;
import com.agenda.financeiro.enums.RegraTaxa;
import com.agenda.financeiro.repository.TransferenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

@Service
public class TransferenciaService {

    @Autowired
    private TransferenciaRepository repository;

    /**
     * Agendar uma transferência com cálculo automático da taxa.
     *
     * @param transferencia Objeto Transferencia com os dados necessários.
     * @return Transferencia agendada e salva no banco.
     */
        public Transferencia agendarTransferencia(Transferencia transferencia) {
        long dias = ChronoUnit.DAYS.between(LocalDate.now(), transferencia.getDataTransferencia());
        transferencia.setTaxa(calcularTaxa(dias, transferencia.getValor()));
        return repository.save(transferencia);
    }

    /**
     * Lista todas as transferências cadastradas.
     *
     * @return Iterable com as transferências salvas.
     */
    public Iterable<Transferencia> listarTransferencias() {
        return repository.findAll();
    }

    /**
     * Busca uma transferência pelo ID.
     *
     * @param id ID da transferência.
     * @return Optional contendo a transferência, caso exista.
     */
    public Optional<Transferencia> buscarTransferenciaPorId(Long id) {
        return repository.findById(id);
    }

    /**
     * Exclui uma transferência pelo ID.
     *
     * @param id ID da transferência.
     * @return True se a exclusão foi bem-sucedida, false caso contrário.
     */
    public boolean excluirTransferencia(Long id) {
        return repository.findById(id).map(transferencia -> {
            repository.delete(transferencia);
            return true;
        }).orElse(false);
    }

    /**
     * Atualiza os dados de uma transferência existente.
     *
     * @param id ID da transferência a ser atualizada.
     * @param transferenciaAtualizada Dados atualizados da transferência.
     * @return Optional contendo a transferência atualizada, caso exista.
     */
    public Optional<Transferencia> atualizarTransferencia(Long id, Transferencia transferenciaAtualizada) {
        return repository.findById(id).map(transferencia -> {
            transferencia.setContaOrigem(transferenciaAtualizada.getContaOrigem());
            transferencia.setContaDestino(transferenciaAtualizada.getContaDestino());
            transferencia.setValor(transferenciaAtualizada.getValor());
            transferencia.setTaxa(transferenciaAtualizada.getTaxa());
            transferencia.setDataAgendamento(transferenciaAtualizada.getDataAgendamento());
            transferencia.setDataTransferencia(transferenciaAtualizada.getDataTransferencia());
            return repository.save(transferencia);
        });
    }

    /**
     * Calcula a taxa com base na tabela de dias e no valor da transferência.
     *
     * @param dias Número de dias entre o agendamento e a transferência.
     * @param valor Valor da transferência.
     * @return Taxa calculada.
     */
    private double calcularTaxa(long dias, double valor) {
        return Arrays.stream(RegraTaxa.values())
                .filter(regra -> regra.aplica(dias))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Data de transferência fora do intervalo permitido (até 50 dias)"))
                .calcular(valor);
    }
}
