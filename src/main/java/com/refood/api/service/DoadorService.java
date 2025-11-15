package com.refood.api.service;

import com.refood.api.entity.Doador;
import com.refood.api.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DoadorService {
    @Autowired private DoadorRepository repository;

    public Doador create(Doador doador) { return repository.save(doador); }

    public List<Doador> listAll() {
        List<Doador> doadores = repository.findAll();

        if(doadores.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum doador cadastrado."
            );
        }else{
            return repository.findAll();
        }
    }

    public Doador getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Doador não encontrado com ID: " + id
                ));
    }

    public Doador update(Long id, Doador doadorAtualizado) {
        Doador existente = getById(id);
        doadorAtualizado.setIdDoador(id);
        return repository.save(doadorAtualizado);
    }

    @Transactional
    public Map<String, Object> deleteWithMessage(Long id) {
        Doador doador = getById(id); // lança 404 se não existir

        // Conta doações e produtos antes de deletar
        long doacoesCount = doador.getDoacoes().size();
        long produtosCount = doador.getDoacoes().stream()
                .flatMap(d -> d.getProdutos().stream())
                .count();

        repository.delete(doador);

        Map<String, Object> response = new HashMap<>();
        response.put("message",
                "Doador deletado com sucesso. " + doacoesCount +
                        " doações e " + produtosCount + " produtos relacionados foram removidos.");
        response.put("doacoesRemovidas", doacoesCount);
        response.put("produtosRemovidos", produtosCount);

        return response;
    }

}