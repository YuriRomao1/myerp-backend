package com.myproject94.myerp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.myproject94.myerp.config.MultiFormatDateDeserializer;
import com.myproject94.myerp.domain.enums.Prioridade;
import com.myproject94.myerp.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChamadoEmpresa implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();

    @JsonDeserialize(using = MultiFormatDateDeserializer.class)
    private LocalDate dataFechamento;

    @JsonDeserialize(using = MultiFormatDateDeserializer.class)
    private LocalDate dataVisita;

    private Prioridade prioridade;

    private Status status;

    private String titulo;

    private String observacoes;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id", nullable = false)
    private Tecnico tecnico;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    // Apenas campos obrigatórios
    public ChamadoEmpresa(Integer id, Prioridade prioridade, Status status, String titulo, String observacoes, Tecnico tecnico, Empresa empresa, BigDecimal valor) {
        this.id = id;
        this.prioridade = prioridade;
        this.status = status;
        this.titulo = titulo;
        this.observacoes = observacoes;
        this.tecnico = tecnico;
        this.empresa = empresa;
        this.valor = valor;
    }

    // Com data de visita
    public ChamadoEmpresa(Integer id, Prioridade prioridade, Status status, String titulo, String observacoes, Tecnico tecnico, Empresa empresa, BigDecimal valor, LocalDate dataVisita) {
        this(id, prioridade, status, titulo, observacoes, tecnico, empresa, valor);
        this.dataVisita = dataVisita;
    }

    // Com data de visita + data de fechamento
    public ChamadoEmpresa(Integer id, Prioridade prioridade, Status status, String titulo, String observacoes, Tecnico tecnico, Empresa empresa, BigDecimal valor, LocalDate dataVisita, LocalDate dataFechamento) {
        this(id, prioridade, status, titulo, observacoes, tecnico, empresa, valor, dataVisita);
        this.dataFechamento = dataFechamento;
    }

    @PrePersist
    public void prePersist() {
        if (dataAbertura == null) {
            dataAbertura = LocalDate.now();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChamadoEmpresa)) return false;
        ChamadoEmpresa that = (ChamadoEmpresa) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}