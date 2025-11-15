// DoacaoRequestDTO.java
package com.refood.api.dto;

import lombok.Data;
import java.util.List;

@Data
public class DoacaoRequestDTO {
    private Long idDoador;
    private Long idOng;
    private List<ProdutoDTO> produtos;
}