package com.myproject94.myerp.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public enum Prioridade {
    BAIXA(0, "BAIXA"),
    MEDIA(1, "MEDIA"),
    ALTA(2,"ALTA");

    private Integer codigo;
    private String descricao;

    private static final Map<Integer, Prioridade> MAPA = Arrays.stream(values())
            .collect(Collectors.toMap(Prioridade::getCodigo, prioridade -> prioridade));

    Prioridade(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static Prioridade toEnum(Integer cod) {
        return Optional.ofNullable(MAPA.get(cod))
                .orElseThrow(() -> new IllegalArgumentException("Prioridade inválida! Código: " + cod));
    }
}