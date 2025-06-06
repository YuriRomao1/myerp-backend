package com.myproject94.myerp.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myproject94.myerp.domain.Cliente;
import com.myproject94.myerp.domain.enums.Perfil;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ClienteDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected Integer id;

    @NotNull(message = "O campo NOME destacado é requerido")
    protected String nome;

    @NotNull(message = "O campo CPF destacado é requerido")
    protected String cpf;

    @NotNull(message = "O campo EMAIL destacado é requerido")
    protected String email;

    @Size(max = 15, message = "Telefone deve ter até 15 caracteres")
    private String telefone;

    @Size(max = 255, message = "Endereço deve ter até 255 caracteres")
    private String endereco;

    protected Set<Integer> perfis = new HashSet<>();

    @JsonFormat(pattern = "dd/MM/yy")
    private LocalDate dataCriacao = LocalDate.now();

    public ClienteDTO(Cliente obj) {
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.telefone = obj.getTelefone();
        this.endereco = obj.getEndereco();;
        this.perfis = obj.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
        this.dataCriacao = obj.getDataCriacao();
        addPerfil(Perfil.BASIC);
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

}