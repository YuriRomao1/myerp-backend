package com.myproject94.myerp.resource;

import com.myproject94.myerp.domain.ChamadoEmpresa;
import com.myproject94.myerp.domain.dtos.ChamadoEmpresaDTO;
import com.myproject94.myerp.service.ChamadoEmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chamados/empresa")
@RequiredArgsConstructor
@Tag(name = "ChamadoEmpresa", description = "Controlador para cadastro e gerenciamento de Chamados de Empresa")
public class ChamadoEmpresaResource {

    private final ChamadoEmpresaService service;

    @GetMapping
    @Operation(summary = "Listar todos os Chamados de Empresa", description = "Retorna lista de todos Chamados de Empresa")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<ChamadoEmpresaDTO>> findAll() {
        List<ChamadoEmpresa> list = service.findAll();
        List<ChamadoEmpresaDTO> dtoList = list.stream()
                .map(ChamadoEmpresaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Chamado de Empresa por ID", description = "Retorna um Chamado de Empresa específico pelo ID")
    @ApiResponse(responseCode = "200", description = "Chamado encontrado")
    @ApiResponse(responseCode = "404", description = "Chamado não encontrado")
    public ResponseEntity<ChamadoEmpresaDTO> findById(@PathVariable Integer id) {
        ChamadoEmpresa obj = service.findById(id);
        return ResponseEntity.ok(new ChamadoEmpresaDTO(obj));
    }

    @PostMapping
    @Operation(summary = "Criar Chamado de Empresa", description = "Cria um novo Chamado de Empresa")
    @ApiResponse(responseCode = "201", description = "Chamado criado com sucesso")
    public ResponseEntity<Void> create(@Valid @RequestBody ChamadoEmpresaDTO dto) {
        ChamadoEmpresa obj = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Chamado de Empresa", description = "Atualiza um Chamado de Empresa existente")
    @ApiResponse(responseCode = "200", description = "Chamado atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Chamado não encontrado")
    public ResponseEntity<ChamadoEmpresaDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ChamadoEmpresaDTO dto) {
        ChamadoEmpresa obj = service.update(id, dto);
        return ResponseEntity.ok(new ChamadoEmpresaDTO(obj));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir Chamado de Empresa", description = "Exclui um Chamado de Empresa pelo ID")
    @ApiResponse(responseCode = "204", description = "Chamado excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Chamado não encontrado")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
