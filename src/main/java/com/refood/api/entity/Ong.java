package com.refood.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ong")
@Data
public class Ong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOng;

    @NotNull(message = "O nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "O CNPJ é obrigatório")
    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @NotNull(message = "A rua é obrigatória")
    @Column(nullable = false)
    private String rua;

    @NotNull(message = "O número é obrigatório")
    @Column(nullable = false)
    private String numero;

    @NotNull(message = "O bairro é obrigatório")
    @Column(nullable = false)
    private String bairro;

    @NotNull(message = "O ano de fundação é obrigatório")
    @Column(name = "ano_fundacao", nullable = false)
    private Integer anoFundacao;

    @NotNull(message = "O número de pessoas ajudadas é obrigatório")
    @Column(name = "num_pessoas_ajudadas", nullable = false)
    private Integer numPessoasAjudadas;

    @OneToMany(mappedBy = "ong", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Doacao> doacoes = new ArrayList<>();
}