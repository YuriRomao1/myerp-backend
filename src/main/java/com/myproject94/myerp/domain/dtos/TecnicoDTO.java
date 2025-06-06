package com.myproject94.myerp.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.myproject94.myerp.config.MultiFormatDateDeserializer;
import com.myproject94.myerp.domain.Tecnico;
import com.myproject94.myerp.domain.enums.Perfil;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TecnicoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected Integer id;

    @NotNull(message = "O campo NOME destacado é requerido")
    protected String nome;

    @NotNull(message = "O campo CPF destacado é requerido")
    protected String cpf;

    @NotNull(message = "O campo TELEFONE é requerido")
    protected String telefone;

    @NotNull(message = "O campo ENDEREÇO é requerido")
    protected String endereco;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy",
            timezone = "UTC")
    protected LocalDate dataNascimento;

    @NotNull(message = "O campo EMAIL destacado é requerido")
    protected String email;

    @NotNull(message = "O campo SENHA destacado é requerido")
    protected String senha;

    protected Set<Integer> perfis = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy",
            timezone = "UTC")
    private LocalDate dataCriacao = LocalDate.now();

    public TecnicoDTO(Tecnico obj) {
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

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }
}