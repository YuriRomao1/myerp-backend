package com.myproject94.myerp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.myproject94.myerp.config.MultiFormatDateDeserializer;
import com.myproject94.myerp.domain.enums.StatusDespesa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Despesas implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String descricao;

    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    @JsonDeserialize(using = MultiFormatDateDeserializer.class)
    private LocalDate dataVencimento;

    @Enumerated(EnumType.STRING)
    private StatusDespesa status;

}
