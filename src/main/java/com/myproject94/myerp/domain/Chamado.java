package com.myproject94.myerp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myproject94.myerp.domain.enums.Prioridade;
import com.myproject94.myerp.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chamado implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;

    private Prioridade prioridade;

    private Status status;

    private String titulo;

    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private  Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    public Chamado(Integer id, Prioridade prioridade, Status status, String titulo, String observacoes, Tecnico tecnico, Cliente cliente, BigDecimal valor) {
        this.id = id;
        this.prioridade = prioridade;
        this.status = status;
        this.titulo = titulo;
        this.observacoes = observacoes;
        this.tecnico = tecnico;
        this.cliente = cliente;
        this.valor = valor;
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
        if (o == null || getClass() != o.getClass()) return false;
        Chamado chamado = (Chamado) o;
        return Objects.equals(id, chamado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
