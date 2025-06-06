package com.myproject94.myerp.service;

import com.myproject94.myerp.domain.ChamadoEmpresa;
import com.myproject94.myerp.domain.dtos.ChamadoEmpresaDTO;
import com.myproject94.myerp.domain.enums.Status;
import com.myproject94.myerp.repositories.ChamadoEmpresaRepository;
import com.myproject94.myerp.repositories.EmpresaRepository;
import com.myproject94.myerp.repositories.TecnicoRepository;
import com.myproject94.myerp.service.exceptions.DataIntegrityViolationException;
import com.myproject94.myerp.service.exceptions.ObjectNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadoEmpresaService {

    private final ChamadoEmpresaRepository repository;
    private final TecnicoService tecnicoService;
    private final EmpresaService empresaService;

    public List<ChamadoEmpresa> findAll() {
        return repository.findAll();
    }

    public ChamadoEmpresa findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "ChamadoEmpresa não encontrado! ID: " + id));
    }

    public ChamadoEmpresa create(@Valid ChamadoEmpresaDTO dto) {
        return repository.save(newChamadoEmpresa(dto));
    }

    public ChamadoEmpresa update(Integer id, @Valid ChamadoEmpresaDTO dto) {
        dto.setId(id);
        ChamadoEmpresa existing = findById(id);
        ChamadoEmpresa updated = newChamadoEmpresa(dto);
        updated.setId(existing.getId());
        return repository.save(updated);
    }

    public void delete(Integer id) {
        ChamadoEmpresa chamado = findById(id);
        repository.delete(chamado);
    }

    private ChamadoEmpresa newChamadoEmpresa(ChamadoEmpresaDTO dto) {
        var tecnico = tecnicoService.findById(dto.getTecnicoId());
        var empresa = empresaService.findById(dto.getEmpresaId());

        ChamadoEmpresa chamado = new ChamadoEmpresa();
        if (dto.getId() != null) {
            chamado.setId(dto.getId());
        }
        chamado.setTecnico(tecnico);
        chamado.setEmpresa(empresa);
        chamado.setPrioridade(dto.getPrioridade());
        chamado.setStatus(dto.getStatus());

        if (dto.getStatus() == Status.ENCERRADO) {
            chamado.setDataFechamento(LocalDate.now());
        }
        chamado.setTitulo(dto.getTitulo());
        chamado.setObservacoes(dto.getObservacoes());
        chamado.setValor(dto.getValor());
        chamado.setDataVisita(dto.getDataVisita());
        return chamado;
    }
}
