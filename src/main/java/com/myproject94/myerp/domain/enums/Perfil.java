package com.myproject94.myerp.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public enum Perfil {

    ADMIN(0, "ROLE_ADMIN"),
    CLIENT(1, "ROLE_CLIENT"),
    TECNICO(2,"ROLE_TECNICO");

    private Integer codigo;

    private String descricao;

    private static final Map<Integer, Perfil> MAPA = Arrays.stream(values())
            .collect(Collectors.toMap(Perfil::getCodigo, perfil -> perfil));

    Perfil(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static Perfil toEnum(Integer cod) {
        return Optional.ofNullable(MAPA.get(cod))
                .orElseThrow(() -> new IllegalArgumentException("Perfil inválido! Código: " + cod));
    }
}
