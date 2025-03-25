package com.myproject94.myerp.domain.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.myproject94.myerp.domain.Chamado;
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
public class ChamadoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;

    @NotNull(message = "O Campo PRIORIDADE é requerido")
    private Integer prioridade;

    @NotNull(message = "O Campo STATUS é requerido")
    private Integer status;

    @NotNull(message = "O Campo TITULO é requerido")
    private String titulo;

    @NotNull(message = "O Campo OBSERVAÇÕES é requerido")
    private String observacoes;

    @NotNull(message = "O Campo TÉCNICO é requerido")
    private Integer tecnico;

    @NotNull(message = "O Campo CLIENTE é requerido")
    private Integer cliente;

    private String nomeTecnico;

    private String nomeCliente;

    private BigDecimal valor;

    public ChamadoDTO(Chamado obj) {
        this.id = obj.getId();
        this.dataAbertura = obj.getDataAbertura();
        this.dataFechamento = obj.getDataFechamento();
        this.prioridade = obj.getPrioridade().getCodigo();
        this.status = obj.getStatus().getCodigo();
        this.titulo = obj.getTitulo();
        this.observacoes = obj.getObservacoes();
        this.tecnico = obj.getTecnico().getId();
        this.cliente = obj.getCliente().getId();
        this.nomeTecnico = obj.getTecnico().getNome();
        this.nomeCliente = obj.getCliente().getNome();
        this.valor = obj.getValor();
    }
}