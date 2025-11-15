// TipoEstabelecimentoConverter.java
package com.refood.api.converter;

import com.refood.api.entity.TipoEstabelecimento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoEstabelecimentoConverter implements AttributeConverter<TipoEstabelecimento, String> {
    @Override
    public String convertToDatabaseColumn(TipoEstabelecimento attribute) {
        return attribute == null ? null : switch (attribute) {
            case COMERCIO -> "Comércio";
            case RESTAURANTE -> "Restaurante";
        };
    }

    @Override
    public TipoEstabelecimento convertToEntityAttribute(String dbData) {
        return dbData == null ? null : switch (dbData) {
            case "Comércio" -> TipoEstabelecimento.COMERCIO;
            case "Restaurante" -> TipoEstabelecimento.RESTAURANTE;
            default -> throw new IllegalArgumentException("Tipo desconhecido: " + dbData);
        };
    }
}