// ProdutoDTO.java
package com.refood.api.dto;

import com.refood.api.entity.Perecivel;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ProdutoDTO {
    private String nome;
    private LocalDate validade;
    private Integer quantidade;
    private Perecivel perecivel;
}