package com.refood.api.service;

import com.refood.api.dto.DoacaoRequestDTO;
import com.refood.api.entity.Doacao;
import com.refood.api.entity.Doador;
import com.refood.api.entity.Ong;
import com.refood.api.entity.Produto;
import com.refood.api.repository.DoacaoRepository;
import com.refood.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    @Transactional
    public Doacao create(DoacaoRequestDTO dto) {
        Doador doador = doadorService.getById(dto.getIdDoador());
        Ong ong = ongService.getById(dto.getIdOng());

        Doacao doacao = new Doacao();
        doacao.setDoador(doador);
        doacao.setOng(ong);
        doacao.setDataDoacao(LocalDateTime.now()); // com hora

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

    public Map<String, Object> buildDoacaoResponse(Doacao doacao) {
        Map<String, Object> response = new HashMap<>();
        response.put("idDoacao", doacao.getIdDoacao());
        response.put("nomeDoador", doacao.getDoador().getNome());
        response.put("nomeOng", doacao.getOng().getNome());
        response.put("dataHoraDoacao", doacao.getDataDoacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        List<Map<String, Object>> itens = doacao.getProdutos().stream().map(p -> {
            Map<String, Object> item = new HashMap<>();
            item.put("nome", p.getNome());
            item.put("quantidade", p.getQuantidade());
            item.put("validade", p.getValidade() != null ? p.getValidade().toString() : null);
            item.put("perecivel", p.getPerecivel().getDescricao());
            return item;
        }).collect(Collectors.toList());

        response.put("itensDoados", itens);
        return response;
    }

    public List<Doacao> listAll() {
        return doacaoRepository.findAll();
    }

    public Doacao getById(Long id) {
        return doacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Doação não encontrada com ID: " + id
                ));
    }

    @Transactional
    public Map<String, Object> deleteWithMessage(Long id) {
        Doacao doacao = getById(id); // lança 404 se não existir

        long produtosCount = doacao.getProdutos().size();

        doacaoRepository.delete(doacao);

        Map<String, Object> response = new HashMap<>();
        response.put("message",
                "Doação deletada com sucesso. " + produtosCount +
                        " produtos relacionados foram removidos.");
        response.put("produtosRemovidos", produtosCount);

        return response;
    }
}