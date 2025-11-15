package com.refood.api.entity;

import com.refood.api.converter.PerecivelConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produto")
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduto;

    @NotNull(message = "O nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    private LocalDate validade;

    private Integer quantidade;

    @NotNull(message = "O campo perecível é obrigatório")
    @Convert(converter = PerecivelConverter.class)
    @Column(name = "perecivel", nullable = false)
    private Perecivel perecivel;

    @ManyToMany(mappedBy = "produtos")
    private List<Doacao> doacoes = new ArrayList<>();
}