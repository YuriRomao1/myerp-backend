package com.myproject94.myerp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myproject94.myerp.domain.enums.StatusDespesa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DespesaFixa implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String descricao;

    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVencimento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPagamento;

    @Enumerated(EnumType.STRING)
    private StatusDespesa status;


    public DespesaFixa(Integer id, String descricao, BigDecimal valor, LocalDate dataVencimento, StatusDespesa status) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.status = status;
    }
}
