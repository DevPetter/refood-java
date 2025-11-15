package com.refood.api.controller;

import com.refood.api.dto.DoacaoRequestDTO;
import com.refood.api.entity.Doacao;
import com.refood.api.service.DoacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doacoes")
public class DoacaoController {

    @Autowired
    private DoacaoService doacaoService;

    /**
     * POST /doacoes
     * Cria uma nova doação com produtos
     */
    @PostMapping
    public ResponseEntity<Doacao> create(@Valid @RequestBody DoacaoRequestDTO dto) {
        Doacao doacao = doacaoService.create(dto);
        return ResponseEntity.ok(doacao);
    }

    /**
     * GET /doacoes
     * Lista todas as doações
     */
    @GetMapping
    public ResponseEntity<List<Doacao>> listAll() {
        return ResponseEntity.ok(doacaoService.listAll());
    }

    /**
     * GET /doacoes/{id}
     * Busca doação por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Doacao> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(doacaoService.getById(id));
    }

    /**
     * DELETE /doacoes/{id}
     * Deleta doação (cascade no banco)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        doacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}