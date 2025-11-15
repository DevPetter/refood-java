package com.refood.api.entity;

import com.refood.api.converter.TipoEstabelecimentoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doador")
@Data
public class Doador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDoador;

    @NotNull(message = "O nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "O CNPJ é obrigatório")
    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @NotNull(message = "O tipo de estabelecimento é obrigatório")
    @Convert(converter = TipoEstabelecimentoConverter.class)
    @Column(name = "tipo_estabelecimento", nullable = false)
    private TipoEstabelecimento tipoEstabelecimento;

    @NotNull(message = "A rua é obrigatória")
    @Column(nullable = false)
    private String rua;

    @NotNull(message = "O número é obrigatório")
    @Column(nullable = false)
    private String numero;

    @NotNull(message = "O bairro é obrigatório")
    @Column(nullable = false)
    private String bairro;

    @OneToMany(mappedBy = "doador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Doacao> doacoes = new ArrayList<>();
}