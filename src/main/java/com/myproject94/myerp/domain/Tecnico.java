package com.myproject94.myerp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.myproject94.myerp.config.MultiFormatDateDeserializer;
import com.myproject94.myerp.domain.dtos.TecnicoDTO;
import com.myproject94.myerp.domain.enums.Perfil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tecnico")
public class Tecnico implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected String nome;

    @Column(unique = true)
    protected String cpf;

    @Size(max = 15)
    private String telefone;

    @Size(max = 255)
    private String endereco;

    @JsonDeserialize(using = MultiFormatDateDeserializer.class)
    private LocalDate dataNascimento;

    @Column(unique = true)
    protected String email;

    @Column(unique = true)
    protected String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfis_tecnico", joinColumns = @JoinColumn(name = "tecnico_id"))
    @Column(name = "perfil")
    protected Set<Integer> perfis = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy",
            timezone = "UTC")
    protected LocalDate dataCriacao = LocalDate.now();

    @JsonIgnore
    @OneToMany(mappedBy = "tecnico")
    private List<Chamado> chamados = new ArrayList<>();

    @PrePersist
    private void DefaultPerfil() {
        if (this.perfis == null || this.perfis.isEmpty()) {
            addPerfis(Perfil.BASIC);
        }
        if (this.dataCriacao == null) {
            this.dataCriacao = LocalDate.now();
        }
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfis(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

    public Tecnico(Integer id, String nome, String cpf, String telefone, String endereco, LocalDate dataNascimento, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
        addPerfis(Perfil.BASIC);
    }

    public Tecnico(TecnicoDTO obj) {
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.telefone = obj.getTelefone();              
        this.endereco = obj.getEndereco();
        this.dataNascimento = obj.getDataNascimento();
        this.email = obj.getEmail();
        this.senha = obj.getSenha();
        this.perfis = obj.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
        this.dataCriacao = obj.getDataCriacao();
    }
}