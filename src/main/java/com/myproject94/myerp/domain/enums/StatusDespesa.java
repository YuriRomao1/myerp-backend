package com.myproject94.myerp.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public enum StatusDespesa {
    PENDENTE (0,"PENDENTE"),
    PAGO (1,"PAGO"),
    ATRASADO(2, "ATRASADO");

    private Integer codigo;
    private String descricao;

    private static final Map<Integer, StatusDespesa> MAPA = Arrays.stream(values())
            .collect(Collectors.toMap(StatusDespesa::getCodigo, statusDespesa -> statusDespesa));

    StatusDespesa(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static StatusDespesa toEnum (Integer cod) {
        return Optional.ofNullable(MAPA.get(cod))
                .orElseThrow(() -> new IllegalArgumentException("Status inválido! Código: " + cod));
    }

}

