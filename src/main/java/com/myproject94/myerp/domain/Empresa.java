package com.myproject94.myerp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.myproject94.myerp.config.MultiFormatDateDeserializer;
import com.myproject94.myerp.domain.dtos.EmpresaDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.Id;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Empresa implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Razão Social é obrigatória")
    @Size(max = 100)
    private String razaoSocial;

    @NotBlank(message = "Nome Fantasia é obrigatório")
    @Size(max = 100)
    private String nomeFantasia;

    @NotBlank(message = "CNPJ é obrigatório")
    @Column(length = 18, nullable = false)
    private String cnpj;

    @NotBlank(message = "E-mail é obrigatório")
    @Size(max = 100)
    @Column(nullable = false)
    private String email;

    @Size(max = 20)
    private String telefone;

    @JsonDeserialize(using = MultiFormatDateDeserializer.class)
    private LocalDate dataCriacao = LocalDate.now();

    @JsonIgnore
    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChamadoEmpresa> chamados = new ArrayList<>();

    public Empresa(Integer id, String razaoSocial, String nomeFantasia, String cnpj, String email, String telefone, LocalDate dataCriacao) {
        this.id = id;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.dataCriacao = dataCriacao;
    }

    public Empresa(EmpresaDTO obj) {
        this.id = obj.getId();
        this.razaoSocial = obj.getRazaoSocial();
        this.nomeFantasia = obj.getNomeFantasia();
        this.cnpj = obj.getCnpj();
        this.email = obj.getEmail();
        this.telefone = obj.getTelefone();
        this.dataCriacao = obj.getDataCriacao();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(id, empresa.id) && Objects.equals(razaoSocial, empresa.razaoSocial) && Objects.equals(nomeFantasia, empresa.nomeFantasia) && Objects.equals(cnpj, empresa.cnpj) && Objects.equals(email, empresa.email) && Objects.equals(telefone, empresa.telefone) && Objects.equals(dataCriacao, empresa.dataCriacao) && Objects.equals(chamados, empresa.chamados);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, razaoSocial, nomeFantasia, cnpj, email, telefone, dataCriacao, chamados);
    }

    @PrePersist
    private void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = LocalDate.now();
        }
    }
}