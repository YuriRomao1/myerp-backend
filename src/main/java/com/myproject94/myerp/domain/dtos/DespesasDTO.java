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
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DespesasDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotNull(message = "O Campo DESCRIÇÃO é requerido")
    private String descricao;

    private BigDecimal valor;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVencimento;

    private StatusDespesa status;

    public DespesasDTO(Despesas despesa) {
        this.id = despesa.getId();
        this.descricao = despesa.getDescricao();
        this.valor = despesa.getValor();
        this.dataVencimento = despesa.getDataVencimento();
        this.status = despesa.getStatus();
    }
}
