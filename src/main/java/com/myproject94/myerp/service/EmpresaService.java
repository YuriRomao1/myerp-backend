package com.myproject94.myerp.service;

import com.myproject94.myerp.domain.Empresa;
import com.myproject94.myerp.domain.dtos.EmpresaDTO;
import com.myproject94.myerp.repositories.EmpresaRepository;
import com.myproject94.myerp.service.exceptions.DataIntegrityViolationException;
import com.myproject94.myerp.service.exceptions.ObjectNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository repository;

    public Empresa findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Empresa não encontrada! Id: " + id));
    }

    public List<Empresa> findAll() {
        return repository.findAll();
    }

    public Empresa create(@Valid EmpresaDTO dto) {
        dto.setId(null);
        validaUnicidade(dto);
        Empresa emp = toEntity(dto);
        return repository.save(emp);
    }

    public Empresa update(Integer id, @Valid EmpresaDTO dto) {
        dto.setId(id);
        Empresa existing = findById(id);
        validaUnicidade(dto);

        existing.setRazaoSocial(dto.getRazaoSocial());
        existing.setNomeFantasia(dto.getNomeFantasia());
        existing.setCnpj(dto.getCnpj());
        existing.setEmail(dto.getEmail());
        existing.setTelefone(dto.getTelefone());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private void validaUnicidade(EmpresaDTO dto) {
        repository.findByCnpj(dto.getCnpj())
                .filter(e -> !e.getId().equals(dto.getId()))
                .ifPresent(e -> {
                    throw new DataIntegrityViolationException(
                            "CNPJ já cadastrado.");
                });

        repository.findByEmail(dto.getEmail())
                .filter(e -> !e.getId().equals(dto.getId()))
                .ifPresent(e -> {
                    throw new DataIntegrityViolationException(
                            "E-mail já cadastrado.");
                });
    }

    private Empresa toEntity(EmpresaDTO dto) {
        return Empresa.builder()
                .id(dto.getId())
                .razaoSocial(dto.getRazaoSocial())
                .nomeFantasia(dto.getNomeFantasia())
                .cnpj(dto.getCnpj())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .dataCriacao(dto.getDataCriacao())
                .build();
    }
}