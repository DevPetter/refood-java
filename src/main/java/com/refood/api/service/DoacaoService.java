package com.refood.api.service;

import com.refood.api.dto.DoacaoRequestDTO;
import com.refood.api.entity.Doacao;
import com.refood.api.entity.Produto;
import com.refood.api.repository.DoacaoRepository;
import com.refood.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoacaoService {

    @Autowired
    private DoacaoRepository doacaoRepository;

    @Autowired
    private DoadorService doadorService;

    @Autowired
    private OngService ongService;

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Cria uma nova doação com produtos
     */
    public Doacao create(DoacaoRequestDTO dto) {
        Doacao doacao = new Doacao();
        doacao.setDoador(doadorService.getById(dto.getIdDoador()));
        doacao.setOng(ongService.getById(dto.getIdOng()));

        // Cria e salva produtos
        List<Produto> produtos = dto.getProdutos().stream().map(p -> {
            Produto produto = new Produto();
            produto.setNome(p.getNome());
            produto.setValidade(p.getValidade());
            produto.setQuantidade(p.getQuantidade());
            produto.setPerecivel(p.getPerecivel());
            return produtoRepository.save(produto);
        }).collect(Collectors.toList());

        doacao.setProdutos(produtos);
        return doacaoRepository.save(doacao);
    }

    /**
     * Lista todas as doações
     */
    public List<Doacao> listAll() {
        return doacaoRepository.findAll();
    }

    /**
     * Busca doação por ID
     */
    public Doacao getById(Long id) {
        return doacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Doação não encontrada com ID: " + id
                ));
    }

    /**
     * Deleta doação por ID (cascade no banco)
     */
    public void delete(Long id) {
        if (!doacaoRepository.existsById(id)) {
            throw new RuntimeException("Doação não encontrada com ID: " + id);
        }
        doacaoRepository.deleteById(id);
    }
}