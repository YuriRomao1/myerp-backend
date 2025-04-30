package com.myproject94.myerp.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myproject94.myerp.domain.Despesas;
import com.myproject94.myerp.domain.enums.StatusDespesa;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DespesasDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotNull(message = "O Campo DESCRIÇÃO é requerido")
    private String descricao;

    private BigDecimal valor;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy",
            timezone = "UTC")
    private LocalDate dataVencimento;

    private StatusDespesa status;

    public DespesasDTO(Despesas despesas) {
        this.id = despesas.getId();
        this.descricao = despesas.getDescricao();
        this.valor = despesas.getValor();
        this.dataVencimento = despesas.getDataVencimento();
        this.status = despesas.getStatus();
    }
}
