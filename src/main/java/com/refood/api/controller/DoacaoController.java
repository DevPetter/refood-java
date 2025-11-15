package com.refood.api.controller;

import com.refood.api.dto.DoacaoRequestDTO;
import com.refood.api.entity.Doacao;
import com.refood.api.service.DoacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<Void> create(@Valid @RequestBody DoacaoRequestDTO dto) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listAll() {
        List<Doacao> doacoes = doacaoService.listAll();
        List<Map<String, Object>> response = doacoes.stream()
                .map(doacaoService::buildDoacaoResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable("id") Long id) {
        Doacao doacao = doacaoService.getById(id);
        Map<String, Object> response = doacaoService.buildDoacaoResponse(doacao);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        Map<String, Object> response = doacaoService.deleteWithMessage(id);
        return ResponseEntity.ok(response);
    }
}