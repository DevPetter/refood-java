package com.refood.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doacao")
@Data
public class Doacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDoacao;

    @ManyToOne
    @JoinColumn(name = "id_doador", nullable = false)
    private Doador doador;

    @ManyToOne
    @JoinColumn(name = "id_ong", nullable = false)
    private Ong ong;

    private LocalDateTime dataDoacao = LocalDateTime.now();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "doacao_produto",
            joinColumns = @JoinColumn(name = "id_doacao"),
            inverseJoinColumns = @JoinColumn(name = "id_produto")
    )
    private List<Produto> produtos = new ArrayList<>();
}