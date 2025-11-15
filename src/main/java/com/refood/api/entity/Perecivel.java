package com.refood.api.entity;

import lombok.Getter;

@Getter
public enum Perecivel {
    SIM("Sim"),
    NAO("Não");

    private final String descricao;

    Perecivel(String descricao) {
        this.descricao = descricao;
    }

    // Opcional: para aceitar "SIM"/"NAO" no JSON
    public static Perecivel fromString(String text) {
        for (Perecivel p : Perecivel.values()) {
            if (p.name().equalsIgnoreCase(text)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Perecivel: " + text);
    }
}