package com.myproject94.myerp.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public enum Status {
    ABERTO(0, "ABERTO"),
    ANDAMENTO(1, "ANDAMENTO"),
    ENCERRADO(2,"ENCERRADO");

    private Integer codigo;
    private String descricao;

    private static final Map<Integer, Status> MAPA = Arrays.stream(values())
            .collect(Collectors.toMap(Status::getCodigo, status -> status));


    Status(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static Status toEnum(Integer cod) {
        return Optional.ofNullable(MAPA.get(cod))
                .orElseThrow(() -> new IllegalArgumentException("Status inválido! Código: " + cod));
    }
}