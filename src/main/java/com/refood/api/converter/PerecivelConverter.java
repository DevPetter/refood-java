// PerecivelConverter.java
package com.refood.api.converter;

import com.refood.api.entity.Perecivel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PerecivelConverter implements AttributeConverter<Perecivel, String> {
    @Override
    public String convertToDatabaseColumn(Perecivel attribute) {
        return attribute == null ? null : switch (attribute) {
            case SIM -> "Sim";
            case NAO -> "Não";
        };
    }

    @Override
    public Perecivel convertToEntityAttribute(String dbData) {
        return dbData == null ? null : switch (dbData) {
            case "Sim" -> Perecivel.SIM;
            case "Não" -> Perecivel.NAO;
            default -> throw new IllegalArgumentException("Perecível inválido: " + dbData);
        };
    }
}