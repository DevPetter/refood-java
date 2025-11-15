package com.refood.api.controller;

import com.refood.api.entity.Doador;
import com.refood.api.service.DoadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doadores")
public class DoadorController {
    @Autowired private DoadorService service;

    @PostMapping
    public ResponseEntity<Doador> create(@Valid @RequestBody Doador doador) {
        return ResponseEntity.ok(service.create(doador));
    }

    @GetMapping
    public ResponseEntity<List<Doador>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doador> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doador> update(@PathVariable("id") Long id, @Valid @RequestBody Doador doador) {
        return ResponseEntity.ok(service.update(id, doador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        Map<String, Object> response = service.deleteWithMessage(id);
        return ResponseEntity.ok(response);
    }
}