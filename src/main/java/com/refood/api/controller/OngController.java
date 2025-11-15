package com.refood.api.controller;

import com.refood.api.entity.Ong;
import com.refood.api.service.OngService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ongs")
public class OngController {
    @Autowired private OngService service;

    @PostMapping
    public ResponseEntity<Ong> create(@Valid @RequestBody Ong ong) {
        return ResponseEntity.ok(service.create(ong));
    }

    @GetMapping
    public ResponseEntity<List<Ong>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ong> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ong> update(@PathVariable("id") Long id, @Valid @RequestBody Ong ong) {
        return ResponseEntity.ok(service.update(id, ong));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        Map<String, Object> response = service.deleteWithMessage(id);
        return ResponseEntity.ok(response);
    }
}