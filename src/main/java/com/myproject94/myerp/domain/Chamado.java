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

@Getter
@Setter
@Entity
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

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVisita;

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

    // Apenas dataAbertura (padrão), sem datas extras
    public Chamado(Integer id, Prioridade prioridade, Status status, String titulo, String observacoes, Tecnico tecnico, Cliente cliente, BigDecimal valor) {
        this.id = id;
        this.dataAbertura = LocalDate.now();
        this.prioridade = prioridade;
        this.status = status;
        this.titulo = titulo;
        this.observacoes = observacoes;
        this.tecnico = tecnico;
        this.cliente = cliente;
        this.valor = valor;
    }

    // Inclui dataVisita
    public Chamado(Integer id, Prioridade prioridade, Status status, String titulo, String observacoes, Tecnico tecnico, Cliente cliente, BigDecimal valor, LocalDate dataVisita) {
        this(id, prioridade, status, titulo, observacoes, tecnico, cliente, valor);
        this.dataVisita = dataVisita;
    }

    // Inclui dataVisita e dataFechamento
    public Chamado(Integer id, Prioridade prioridade, Status status, String titulo, String observacoes, Tecnico tecnico, Cliente cliente, BigDecimal valor, LocalDate dataVisita, LocalDate dataFechamento) {
        this(id, prioridade, status, titulo, observacoes, tecnico, cliente, valor, dataVisita);
        this.dataFechamento = dataFechamento;
    }

    // Totalmente customizado: dataAbertura, dataFechamento
    public Chamado(Integer id, LocalDate dataAbertura, LocalDate dataFechamento, Prioridade prioridade, Status status, String titulo, String observacoes, Tecnico tecnico, Cliente cliente, BigDecimal valor) {
        this.id = id;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
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
