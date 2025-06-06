package com.myproject94.myerp.resource;

import com.myproject94.myerp.domain.Empresa;
import com.myproject94.myerp.domain.dtos.EmpresaDTO;
import com.myproject94.myerp.service.EmpresaService;
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
@RequestMapping("/empresas")
@RequiredArgsConstructor
@Tag(name = "Empresa", description = "Controlador para cadastro e gerenciamento de Empresas")
public class EmpresaResource {

    private final EmpresaService service;

    @GetMapping
    @Operation(summary = "Listar todas as Empresas", description = "Retorna lista de todas as Empresas")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<EmpresaDTO>> findAll() {
        List<Empresa> list = service.findAll();
        List<EmpresaDTO> dtoList = list.stream()
                .map(EmpresaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Empresa por ID", description = "Retorna uma Empresa específica pelo ID")
    @ApiResponse(responseCode = "200", description = "Empresa encontrada")
    @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    public ResponseEntity<EmpresaDTO> findById(@PathVariable Integer id) {
        Empresa obj = service.findById(id);
        return ResponseEntity.ok(new EmpresaDTO(obj));
    }

    @PostMapping
    @Operation(summary = "Criar Empresa", description = "Cria uma nova Empresa")
    @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso")
    public ResponseEntity<Void> create(@Valid @RequestBody EmpresaDTO dto) {
        Empresa obj = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Empresa", description = "Atualiza uma Empresa existente")
    @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    public ResponseEntity<EmpresaDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EmpresaDTO dto) {
        Empresa obj = service.update(id, dto);
        return ResponseEntity.ok(new EmpresaDTO(obj));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir Empresa", description = "Exclui uma Empresa pelo ID")
    @ApiResponse(responseCode = "204", description = "Empresa excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
