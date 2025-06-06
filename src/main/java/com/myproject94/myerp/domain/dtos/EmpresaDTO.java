package com.myproject94.myerp.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myproject94.myerp.domain.Empresa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaDTO implements Serializable {

    private Integer id;

    @NotBlank(message = "Razão Social é obrigatória")
    @Size(max = 100)
    private String razaoSocial;

    @NotBlank(message = "Nome Fantasia é obrigatório")
    @Size(max = 100)
    private String nomeFantasia;

    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;

    @NotBlank(message = "E-mail é obrigatório")
    private String email;

    @Size(max = 20)
    private String telefone;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy",
            timezone = "UTC")
    private LocalDate dataCriacao = LocalDate.now();

    public EmpresaDTO(Empresa e) {
        this.id           = e.getId();
        this.razaoSocial  = e.getRazaoSocial();
        this.nomeFantasia = e.getNomeFantasia();
        this.cnpj         = e.getCnpj();
        this.email        = e.getEmail();
        this.telefone     = e.getTelefone();
        this.dataCriacao  = e.getDataCriacao();
    }
}