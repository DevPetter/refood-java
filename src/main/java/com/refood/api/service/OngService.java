package com.refood.api.service;

import com.refood.api.entity.Doador;
import com.refood.api.entity.Ong;
import com.refood.api.repository.OngRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OngService {
    @Autowired
    private OngRepository repository;

    public Ong create(Ong ong) {
        return repository.save(ong);
    }

    public List<Ong> listAll() {
        List<Ong> ongs = repository.findAll();

        if (ongs.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhuma Ong cadastrada."
            );
        }
        return repository.findAll();
    }

    public Ong getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ong não encontrada com ID: " + id
                ));
    }

    public Ong update(Long id, Ong ongAtualizada) {
        Ong existente = getById(id);

        if (existente == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Ong não encontrada com ID " + id
            );
        } else {
            ongAtualizada.setIdOng(id);
            return repository.save(ongAtualizada);
        }
    }

    @Transactional
    public Map<String, Object> deleteWithMessage(Long id) {
        Ong ong = getById(id); // lança 404 se não existir

        long doacoesCount = ong.getDoacoes().size();
        long produtosCount = ong.getDoacoes().stream()
                .flatMap(d -> d.getProdutos().stream())
                .count();

        repository.delete(ong);

        Map<String, Object> response = new HashMap<>();
        response.put("message",
                "ONG deletada com sucesso. " + doacoesCount +
                        " doações e " + produtosCount + " produtos relacionados foram removidos.");
        response.put("doacoesRemovidas", doacoesCount);
        response.put("produtosRemovidos", produtosCount);

        return response;
    }
}