package com.myproject94.myerp.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myproject94.myerp.domain.ChamadoEmpresa;
import com.myproject94.myerp.domain.enums.Prioridade;
import com.myproject94.myerp.domain.enums.Status;
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
public class ChamadoEmpresaDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy",
            timezone = "UTC")
    private LocalDate dataAbertura;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy",
            timezone = "UTC")
    private LocalDate dataFechamento;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy",
            timezone = "UTC")
    private LocalDate dataVisita;

    @NotNull(message = "Prioridade é obrigatória")
    private Prioridade prioridade;

    @NotNull(message = "Status é obrigatório")
    private Status status;

    @NotNull(message = "Título é obrigatório")
    private String titulo;

    private String observacoes;

    @NotNull(message = "Técnico é obrigatório")
    private Integer tecnicoId;

    @NotNull(message = "Empresa é obrigatória")
    private Integer empresaId;

    private BigDecimal valor;

    public ChamadoEmpresaDTO(ChamadoEmpresa obj) {
        this.id = obj.getId();
        this.dataAbertura = obj.getDataAbertura();
        this.dataFechamento = obj.getDataFechamento();
        this.dataVisita = obj.getDataVisita();
        this.prioridade = obj.getPrioridade();
        this.status = obj.getStatus();
        this.titulo = obj.getTitulo();
        this.observacoes = obj.getObservacoes();
        this.tecnicoId = obj.getTecnico().getId();
        this.empresaId = obj.getEmpresa().getId();
        this.valor = obj.getValor();
    }
}
