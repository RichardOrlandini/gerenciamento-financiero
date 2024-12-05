package com.agenda.financeiro.controller;

import com.agenda.financeiro.domain.Transferencia;
import com.agenda.financeiro.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaController {

    @Autowired
    private TransferenciaService service;

    @GetMapping
    public ResponseEntity<Iterable<Transferencia>> listar() {
        return ResponseEntity.ok(service.listarTransferencias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transferencia> buscarPorId(@PathVariable Long id) {
        return service.buscarTransferenciaPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Transferencia> agendar(@RequestBody Transferencia transferencia) {
        return ResponseEntity.ok(service.agendarTransferencia(transferencia));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transferencia> atualizar(
            @PathVariable Long id, @RequestBody Transferencia transferenciaAtualizada) {
        return service.atualizarTransferencia(id, transferenciaAtualizada)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        return service.excluirTransferencia(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}